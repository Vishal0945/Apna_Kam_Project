package com.payment.payment_service.serviceImpl;

import java.util.Map;
import java.util.Optional;

import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.payment.payment_service.entity.Payment;
import com.payment.payment_service.entity.PaymentStatus;
import com.payment.payment_service.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

@Service
public class PaymentService {

	@Value("${razorpay.key}")
	private String key;

	@Value("${razorpay.secret}")
	private String secret;

	@Autowired
	private PaymentRepository repository;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private String generateNextOrderId() {

		Optional<Payment> latestPayment = repository.findTopByOrderByIdDesc();

		if (latestPayment.isPresent()) {

			String lastOrderId = latestPayment.get().getOrderId(); // ORD_1003

			if (lastOrderId != null) {
				int lastNumber = Integer.parseInt(lastOrderId.split("_")[1]);
				int nextNumber = lastNumber + 1;

				return "ORD_" + nextNumber;
			}
		}

		// If no records exist
		return "ORD_1001";
	}

public Map<String, Object> createOrder(String regMobile, Double amount) throws Exception {

    // 1️⃣ Generate order id
    String generatedOrderId = generateNextOrderId();

    RazorpayClient client = new RazorpayClient(key, secret);

    // 2️⃣ Convert amount to paise
    long amountInPaise = Math.round(amount * 100);

    JSONObject options = new JSONObject();
    options.put("amount", amountInPaise);
    options.put("currency", "INR");
    options.put("receipt", generatedOrderId);

    Order razorpayOrder = client.orders.create(options);

    Payment payment = new Payment();

    Integer userId = null;

    // 3️⃣ Fetch user from mobile number
    if (regMobile != null && !regMobile.trim().isEmpty()) {

        String sql = "SELECT registration_id FROM public.registration WHERE mobile_number = ?";

        try {
            userId = jdbcTemplate.queryForObject(sql, Integer.class, regMobile);
            System.out.println("User found: " + userId);

        } catch (EmptyResultDataAccessException e) {
            System.out.println("Mobile number not found");
        }
    }

    // 4️⃣ Stop if user not found
    if (userId == null) {
        throw new RuntimeException("Cannot create payment. Mobile not registered.");
    }

    // 5️⃣ Save payment in DB
    payment.setUserId(userId);
    payment.setRegMobile(regMobile);
    payment.setOrderId(generatedOrderId);
    payment.setAmount(amount); // save original rupees
    payment.setCurrency("INR");
    payment.setGatewayOrderId(razorpayOrder.get("id"));
    payment.setStatus(PaymentStatus.CREATED);

    repository.save(payment);

    // 6️⃣ Response to frontend
    return Map.of(
            "orderId", generatedOrderId,
            "gatewayOrderId", razorpayOrder.get("id"),
            "amount", amountInPaise,
            "currency", "INR"
    );
}

public ResponseEntity<?> verifyPayment(String orderId, String paymentId, String signature) {

    try {

        // 1️⃣ Validate request
        if (orderId == null || paymentId == null || signature == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("status", "FAILED", "message", "Missing parameters"));
        }

        // 2️⃣ Fetch order from DB
        Payment payment = repository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // 3️⃣ Verify Razorpay Signature
        JSONObject options = new JSONObject();
        options.put("razorpay_order_id", payment.getGatewayOrderId()); // IMPORTANT
        options.put("razorpay_payment_id", paymentId);
        options.put("razorpay_signature", signature);

        Utils.verifyPaymentSignature(options, secret);

        // 4️⃣ Fetch payment details from Razorpay
        RazorpayClient client = new RazorpayClient(key, secret);
        com.razorpay.Payment razorPayment = client.payments.fetch(paymentId);

        String razorStatus = razorPayment.get("status");

        // 5️⃣ Update status in DB
        if ("captured".equalsIgnoreCase(razorStatus)) {

            payment.setStatus(PaymentStatus.SUCCESS);

        } else if ("failed".equalsIgnoreCase(razorStatus)) {

            payment.setStatus(PaymentStatus.FAILED);

        } else if ("authorized".equalsIgnoreCase(razorStatus)) {

            payment.setStatus(PaymentStatus.PENDING);

        } else {

            payment.setStatus(PaymentStatus.PENDING);
        }

        payment.setPaymentId(paymentId);
        payment.setSignature(signature);

        repository.save(payment);

        return ResponseEntity.ok(
                Map.of(
                        "orderId", orderId,
                        "paymentId", paymentId,
                        "razorpayStatus", razorStatus,
                        "dbStatus", payment.getStatus()
                ));

    } catch (RazorpayException e) {

        return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST)
                .body(Map.of("status", "FAILED", "message", "Signature verification failed"));

    } catch (RuntimeException e) {

        return ResponseEntity.status(HttpStatus.SC_NOT_FOUND)
                .body(Map.of("status", "FAILED", "message", e.getMessage()));

    } catch (Exception e) {

        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Map.of("status", "FAILED", "message", "Verification failed"));
    }
}

	public Map<String, Object> getPaymentStatus(String orderId) {

		Payment payment = repository.findByOrderId(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

		return Map.of("orderId", payment.getOrderId(), "amount", payment.getAmount(), "currency", payment.getCurrency(),
				"status", payment.getStatus(), "paymentId", payment.getPaymentId(), "createdAt",
				payment.getCreatedAt());
	}

}






//public boolean verifyPayment(String orderId, String paymentId, String signature) {
//    try {
//        JSONObject options = new JSONObject();
//        options.put("razorpay_order_id", orderId);
//        options.put("razorpay_payment_id", paymentId);
//        options.put("razorpay_signature", signature);
//
//        // Sahi call: secret key pass karna zaroori hai
//        Utils.verifyPaymentSignature(options, keySecret);
//
//        return true;
//    } catch (RazorpayException e) {
//        // Log kar le for debugging
//        System.err.println("Signature verification failed: " + e.getMessage());
//        return false;
//    }
//}

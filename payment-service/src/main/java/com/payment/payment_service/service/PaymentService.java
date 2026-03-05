package com.payment.payment_service.service;

import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.razorpay.Utils;
import com.payment.payment_service.entity.Payment;
import com.payment.payment_service.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


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

    // Generate next Order ID
    String generatedOrderId = generateNextOrderId();

    RazorpayClient client = new RazorpayClient(key, secret);

    JSONObject options = new JSONObject();
    options.put("amount", Math.round(amount));
    options.put("currency", "INR");
    options.put("receipt", generatedOrderId);

    Order razorpayOrder = client.orders.create(options);

    Payment payment = new Payment();

    Integer userId = null;   // ✅ Declare outside

    if (regMobile != null && !regMobile.trim().isEmpty()) {

        String sql = "SELECT registration_id FROM public.registration WHERE mobile_number = ?";

        try {
            userId = jdbcTemplate.queryForObject(sql, Integer.class, regMobile);
            System.out.println("Mobile number exists. User ID: " + userId);

        } catch (EmptyResultDataAccessException e) {
            System.out.println("Mobile number not found");
        }
    }

    // Optional: Stop payment if user not found
    if (userId == null) {
        throw new RuntimeException("Cannot create payment. Mobile not registered.");
    }

    payment.setUserId(userId);
    payment.setRegMobile(regMobile);
    payment.setOrderId(generatedOrderId);
    payment.setAmount(amount);
    payment.setCurrency("INR");
    payment.setGatewayOrderId(razorpayOrder.get("id"));
    payment.setStatus("CREATED");

    repository.save(payment);

    return Map.of(
            "orderId", generatedOrderId,
            "gatewayOrderId", razorpayOrder.get("id"),
            "amount", razorpayOrder.get("amount"),
            "currency", "INR"
    );
}

    public String verifyPayment(String orderId, String paymentId, String signature) throws Exception {

        Payment payment = repository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        JSONObject options = new JSONObject();
        options.put("razorpay_order_id", payment.getGatewayOrderId());
        options.put("razorpay_payment_id", paymentId);
        options.put("razorpay_signature", signature);

        Utils.verifyPaymentSignature(options, secret);

        payment.setPaymentId(paymentId);
        payment.setSignature(signature);
        payment.setStatus("SUCCESS");

        repository.save(payment);

        return "Payment Verified";
    }
    
    public Map<String, Object> getPaymentStatus(String orderId) {

        Payment payment = repository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return Map.of(
                "orderId", payment.getOrderId(),
                "amount", payment.getAmount(),
                "currency", payment.getCurrency(),
                "status", payment.getStatus(),
                "paymentId", payment.getPaymentId(),
                "createdAt", payment.getCreatedAt()
        );
    }

}


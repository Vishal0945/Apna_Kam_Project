package com.payment.payment_service.service;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.Utils;
import com.payment.payment_service.entity.Payment;
import com.payment.payment_service.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;


@Service
public class PaymentService {

    @Value("${razorpay.key}")
    private String key;

    @Value("${razorpay.secret}")
    private String secret;

    @Autowired
    private PaymentRepository repository;

    public Map<String, Object> createOrder(String orderId, Double amount) throws Exception {

        RazorpayClient client = new RazorpayClient(key, secret);

        JSONObject options = new JSONObject();
        options.put("amount", amount * 100);
        options.put("currency", "INR");
        options.put("receipt", orderId);

        Order order = client.orders.create(options);

        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setAmount(amount);
        payment.setCurrency("INR");
        payment.setGatewayOrderId(order.get("id"));
        payment.setStatus("CREATED");

        repository.save(payment);

        return Map.of(
                "gatewayOrderId", order.get("id"),
                "amount", order.get("amount"),
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


package com.payment.payment_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.payment.payment_service.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(
            @RequestParam String regMobile,
            @RequestParam Double amount) throws Exception {

        return ResponseEntity.ok(service.createOrder(regMobile, amount));
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(
            @RequestParam String orderId,
            @RequestParam String paymentId,
            @RequestParam String signature) throws Exception {

        return ResponseEntity.ok(service.verifyPayment(orderId, paymentId, signature));
    }

    @GetMapping("/status/{orderId}")
    public ResponseEntity<?> status(@PathVariable String orderId) {

        return ResponseEntity.ok(
                service.getPaymentStatus(orderId)
        );
    }
    
    @PostMapping("/webhook")
    public ResponseEntity<?> webhook(@RequestBody String payload,
                                     @RequestHeader("X-Razorpay-Signature") String header)
            throws Exception {

        // Verify webhook signature
        // Update payment status
        return ResponseEntity.ok("Webhook processed");
    }
    
    

}


package com.payment.payment_service.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;
    private String userId;
    private Double amount;
    private String currency;
    private String gatewayOrderId;
    private String paymentId;
    private String signature;
    private String status;
    private String refundId;

    private LocalDateTime createdAt = LocalDateTime.now();

    // getters & setters
}


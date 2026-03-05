package com.payment.payment_service.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private Integer userId;
    private Double amount;
    private String currency;
    private String gatewayOrderId;
    private String paymentId;
    private String signature;
    
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private String refundId;
    private String regMobile;
    

    private LocalDateTime createdAt = LocalDateTime.now();

    // getters & setters
}


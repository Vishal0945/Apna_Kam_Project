package com.payment.payment_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "service_pricing")
@Data
public class ServicePricing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceType;
    
    private String serviceSubType;


    private Double basePrice;

    private Double commissionPercent;

    private Boolean active;
} 

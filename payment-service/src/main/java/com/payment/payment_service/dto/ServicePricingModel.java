package com.payment.payment_service.dto;

import lombok.Data;

@Data
public class ServicePricingModel {

    private String serviceType;
    
    private String serviceSubType;

    private Double basePrice;

    private Boolean active;

}

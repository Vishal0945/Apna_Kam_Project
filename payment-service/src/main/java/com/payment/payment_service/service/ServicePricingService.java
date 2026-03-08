package com.payment.payment_service.service;

import com.payment.payment_service.dto.ResponseModel;
import com.payment.payment_service.dto.ServicePricingModel;

public interface ServicePricingService {

    ResponseModel addServicePricing(ServicePricingModel model);

    ResponseModel getAllServices();

    ResponseModel updateServicePricing(Long id, ServicePricingModel model);

    ResponseModel disableService(Long id);

}

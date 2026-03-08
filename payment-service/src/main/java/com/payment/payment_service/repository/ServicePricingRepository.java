package com.payment.payment_service.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payment.payment_service.entity.ServicePricing;


public interface ServicePricingRepository extends JpaRepository<ServicePricing, Long>{

    Optional<ServicePricing> findByServiceTypeAndActive(String serviceType, Boolean active);

}

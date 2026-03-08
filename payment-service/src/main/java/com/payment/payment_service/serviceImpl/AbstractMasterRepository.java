package com.payment.payment_service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.payment.payment_service.repository.ServicePricingRepository;
import com.payment.payment_service.repository.ServiceTypeRepo;
import com.payment.payment_service.repository.WorkCategoryRepo;

abstract class AbstractMasterRepository {
	
	 @Autowired
	 ServicePricingRepository servicePricingRepository;
	 
	 @Autowired
	 WorkCategoryRepo workCategoryRepo;
	 
	 @Autowired
	 ServiceTypeRepo serviceTypeRepo;
	
	
	
	
	
	
	
	
	
	
	
	
	
//@Autowired
//OtpRepository otpRepository;
//
//@Autowired
//RegistrationRepo registrationRepo;
//@Autowired
//WorkerDetailsRepo workerDetailsRepo;
//@Autowired
//WorkCategoryRepo workCategoryRepo;
//@Autowired
//ServiceTypeRepo serviceTypeRepo;
//
//@Autowired
//AddressMapper mapper;
//@Autowired
//AddressRepository repository;

}

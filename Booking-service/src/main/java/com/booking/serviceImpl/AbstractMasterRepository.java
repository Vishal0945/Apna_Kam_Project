package com.booking.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.booking.Repo.BookingRepository;
import com.booking.Repo.WorkerDetailsRepo;

abstract class AbstractMasterRepository {
	
	 @Autowired
	  BookingRepository bookingRepository;
	 
	@Autowired
	WorkerDetailsRepo workerDetailsRepo;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//@Autowired
//OtpRepository otpRepository;
//
//@Autowired
//RegistrationRepo registrationRepo;

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

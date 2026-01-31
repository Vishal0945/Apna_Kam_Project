package com.apnakam.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.apnakam.repository.OtpRepository;
import com.apnakam.repository.RegistrationRepo;
import com.apnakam.repository.ServiceTypeRepo;
import com.apnakam.repository.WorkCategoryRepo;
import com.apnakam.repository.WorkerDetailsRepo;

abstract class AbstractMasterRepository {
@Autowired
OtpRepository otpRepository;

@Autowired
RegistrationRepo registrationRepo;
@Autowired
WorkerDetailsRepo workerDetailsRepo;
@Autowired
WorkCategoryRepo workCategoryRepo;
@Autowired
ServiceTypeRepo serviceTypeRepo;

}

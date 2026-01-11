package com.apnakam.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.apnakam.repository.OtpRepository;
import com.apnakam.repository.RegistrationRepo;

abstract class AbstractMasterRepository {
@Autowired
OtpRepository otpRepository;

@Autowired
RegistrationRepo registrationRepo;
}

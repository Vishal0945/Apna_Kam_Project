package com.apnakam.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.apnakam.repository.OtpRepository;

abstract class AbstractMasterRepository {
@Autowired
OtpRepository otpRepository;
}

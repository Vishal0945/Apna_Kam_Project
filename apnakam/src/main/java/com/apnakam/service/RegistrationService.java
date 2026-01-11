package com.apnakam.service;

public interface RegistrationService {

	int getsum();

	String verifyOtp(String mobile, String otp);

	String generateOtp();

	void saveOtp(String mobile, String otp);

//	void sendOtpSms(String mobile, String otp);

}

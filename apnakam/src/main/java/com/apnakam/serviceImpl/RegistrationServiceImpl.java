package com.apnakam.serviceImpl;

import java.time.LocalDateTime;
import java.util.Random;

import org.hibernate.query.spi.AbstractCommonQueryContract;
import org.springframework.stereotype.Service;

import com.apnakam.constant.CommonConstant;
import com.apnakam.entity.OtpDetails;
import com.apnakam.entity.Registration;
import com.apnakam.service.RegistrationService;

@Service
public class RegistrationServiceImpl extends AbstractMasterRepository implements RegistrationService{
	CommonConstant commonConstant =new CommonConstant(); 
	@Override
	public int getsum() {
		int a = 10;

		int b = 20;
		return a + b;

	}
	
	
	 

	    public String generateOtp() {
	        return String.valueOf(100000 + new Random().nextInt(900000));
	    }

	    public LocalDateTime getExpiryTime() {
	        return LocalDateTime.now().plusMinutes(commonConstant.OTP_EXPIRY_MINUTES);
	    }
	    
	    public void saveOtp(String mobile, String otp) {
	        OtpDetails otpDetails = new OtpDetails();
	        Registration reg= new Registration();
	        reg.setMobileNumber(mobile);
	        otpDetails.setMobileNumber(mobile);
	        otpDetails.setOtp(otp);
	        otpDetails.setExpiryTime(getExpiryTime());
	        otpDetails.setVerified(false);

	        otpRepository.save(otpDetails);
	    }
	    
	    
	    public String verifyOtp(String mobile, String enteredOtp) {

	        OtpDetails otpDetails = otpRepository.findById(mobile)
	                .orElseThrow(() -> new RuntimeException("OTP not found"));

	        if (otpDetails.isVerified()) {
	            return "OTP already used";
	        }

	        if (otpDetails.getExpiryTime().isBefore(LocalDateTime.now())) {
	            return "OTP expired";
	        }

	        if (!otpDetails.getOtp().equals(enteredOtp)) {
	            return "Invalid OTP";
	        }

	        otpDetails.setVerified(true);
	        otpRepository.save(otpDetails);

	        return "OTP verified successfully";
	    }



}

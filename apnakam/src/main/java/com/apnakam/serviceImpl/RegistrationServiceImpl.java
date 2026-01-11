package com.apnakam.serviceImpl;

import java.time.LocalDateTime;
import java.util.Random;

import org.hibernate.query.spi.AbstractCommonQueryContract;
import org.springframework.stereotype.Service;

import com.apnakam.constant.CommonConstant;
import com.apnakam.entity.OtpDetails;
import com.apnakam.entity.Registration;
import com.apnakam.service.RegistrationService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RegistrationServiceImpl extends AbstractMasterRepository implements RegistrationService{
	@Value("${msg91.authkey}")
	private String authKey;

	@Value("${msg91.sender}")
	private String senderId;
	
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
	        Boolean present = registrationRepo.existsByMobileNumber(mobile);
	      if(present!=true) {
	        Registration reg= new Registration();
	        reg.setMobileNumber(mobile);
	        registrationRepo.save(reg);

	        }
	      
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

	  

//	    public void sendOtpSms(String mobile, String otp) {
//
//	        String message = "Your OTP is " + otp + ". Valid for 5 minutes.";
//
//	        String url = "https://api.msg91.com/api/v2/sendsms";
//
//	        HttpHeaders headers = new HttpHeaders();
//	        headers.setContentType(MediaType.APPLICATION_JSON);
//	        headers.set("authkey", authKey);   // 🔥 from properties
//
//	        String body = "{"
//	                + "\"sender\":\"" + senderId + "\","
//	                + "\"route\":\"4\","
//	                + "\"country\":\"91\","
//	                + "\"sms\":[{"
//	                + "\"message\":\"" + message + "\","
//	                + "\"to\":[\"" + mobile + "\"]"
//	                + "}]"
//	                + "}";
//
//	        RestTemplate restTemplate = new RestTemplate();
//	        HttpEntity<String> entity = new HttpEntity<>(body, headers);
//	        restTemplate.postForObject(url, entity, String.class);
//	    }
}

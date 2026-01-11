package com.apnakam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apnakam.service.RegistrationService;

@RestController
//@CrossOrigin
@RequestMapping("/credential")
public class UserRegistrationController {
	@Autowired	
	RegistrationService service;
	
	@GetMapping("/hello")
	public String hello() {
		return "api is running properly...";
	}
	
	@GetMapping("/sum")
	public int sum()
	{
		return service.getsum();
		
		
	}
	 @PostMapping("/sendRegistratioNumber")
	    public ResponseEntity<String> sendOtp(
	            @RequestParam String mobile) {

	        // ✅ Call generateOtp()
	        String otp = service.generateOtp();

	        // ✅ Call saveOtp()
	        service.saveOtp(mobile, otp);
//	        service.sendOtpSms(mobile, otp); // 🔥 SMS SENT HERE

	        // TODO: Send OTP via SMS / Email
	        System.out.println("OTP sent: " + otp);

	        return ResponseEntity.ok("OTP sent successfully");
	    }
	  @PostMapping("/verify")
	    public ResponseEntity<String> verifyOtp(
	            @RequestParam String mobile,
	            @RequestParam String otp) {

	        String result = service.verifyOtp(mobile, otp);
	        return ResponseEntity.ok(result);
	    }
	  
	
}

//http://localhost:9001/credential/verify?mobile=8860244730&otp=695407
//http://localhost:9001/credential/send?mobile=8860244730
	
	

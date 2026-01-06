package com.apnakam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apnakam.service.RegistrationService;

@RestController
//@CrossOrigin
@RequestMapping("/credential")
public class UserRegistrationController {
//	@Autowired	
//	RegistrationService service;
	
	@GetMapping("/hello")
	public String hello() {
		return "api is running properly...";
	}
}

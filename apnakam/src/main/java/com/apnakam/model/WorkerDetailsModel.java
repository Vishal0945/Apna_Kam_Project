package com.apnakam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerDetailsModel {

	    private String fullName;
	    private String mobileNo;
	    private String email;
	    private String serviceType;
	    private int experienceYears;
	    private Double basePrice;
	    private String city;
	    private Boolean availability;
	    private Double rating;
	    private String status;
	    private String loginPassword;
	
		
	

}

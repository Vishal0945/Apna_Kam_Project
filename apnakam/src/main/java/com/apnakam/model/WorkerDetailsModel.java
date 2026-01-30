package com.apnakam.model;

import lombok.Data;

@Data
public class WorkerDetailsModel {

    private String fullName;
    private String mobileNo;
    private String email;
    private String serviceType;
    private int experienceYears;
    private Double basePrice;
    private String city;
    private String workerId;
	public Boolean availability ;
	public Double rating;
	public String status;
	public Long id;
		
	

}

package com.booking.model;

import lombok.Data;

@Data
public class BookingModel {
	  private String customerName;
	    private String customerMobile;
	    private String workerId;
	    private String serviceType;
	    private String serviceSubType;
	    private String bookingDate;
	    private String slot;
	    private String address;
	    private Double amount;
}


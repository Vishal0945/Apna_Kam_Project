package com.payment.payment_service.dto;

import lombok.Data;

@Data
public class ResponseModel {
	
	private Object message;
	private Object data;
	private Object httpStatus;
    private String name;
    
}

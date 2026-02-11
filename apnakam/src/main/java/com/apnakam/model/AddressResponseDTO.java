package com.apnakam.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponseDTO {

	 private Long id;
	    private String addressLine1;
	    private String addressLine2;
	    private String landmark;
	    private String city;
	    private String state;
	    private String pincode;
	    private Double latitude;
	    private Double longitude;
	    private String addressType;
	    private Boolean isDefault;
	    private String mobileNumber;
}

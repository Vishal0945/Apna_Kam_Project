package com.apnakam.model;

import com.google.firebase.database.annotations.NotNull;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class AddressRequestDTO {

    @NotBlank
    private String addressLine1;

    private String addressLine2;
    private String landmark;
    private String city;
    private String state;
    private String pincode;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    private String addressType;
    private Boolean isDefault;
    private String mobileNumber;
    private String regMobileNumber;
}

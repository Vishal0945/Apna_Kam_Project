package com.apnakam.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class OtpDetails {

    @Id
    private String mobileNumber;

    private String otp;
    private LocalDateTime expiryTime;
    private boolean verified;
}


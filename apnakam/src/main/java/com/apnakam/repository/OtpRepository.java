package com.apnakam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apnakam.entity.OtpDetails;

public interface OtpRepository extends JpaRepository<OtpDetails,String > {

    Optional<OtpDetails> findTopByMobileNumberOrderByExpiryTimeDesc(String mobileNumber);

	
}

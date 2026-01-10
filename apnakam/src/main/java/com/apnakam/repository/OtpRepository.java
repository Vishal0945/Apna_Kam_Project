package com.apnakam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apnakam.entity.OtpDetails;

public interface OtpRepository extends JpaRepository<OtpDetails,String > {

}

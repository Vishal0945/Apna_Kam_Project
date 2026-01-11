package com.apnakam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apnakam.entity.Registration;

@Repository
public interface RegistrationRepo extends JpaRepository<Registration,Long > {

	Boolean existsByMobileNumber(String mobile);

}

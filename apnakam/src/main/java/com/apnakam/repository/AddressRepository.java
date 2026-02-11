package com.apnakam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apnakam.entity.UserAddress;

public interface AddressRepository extends JpaRepository<UserAddress,Long > {

	List<UserAddress> findByUserIdAndIsActiveTrueOrderByIsDefaultDescCreatedAtDesc(Long userId);

	List<UserAddress> findByUserIdAndIsActiveTrueOrderByCreatedAtDesc(Long userId);

	Optional<UserAddress> findByIdAndUserIdAndIsActiveTrue(Long id, Long userId);

	
}

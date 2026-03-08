package com.payment.payment_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payment.payment_service.entity.ServiceType;



@Repository
public interface ServiceTypeRepo extends JpaRepository<ServiceType,Integer > {


	List<ServiceType> findByCategoryId(Integer id);


	ServiceType findByServiceNameIgnoreCase(String serviceSubType);


	

		
}

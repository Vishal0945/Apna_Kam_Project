package com.payment.payment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payment.payment_service.entity.WorkCategory;


@Repository
public interface WorkCategoryRepo extends JpaRepository<WorkCategory,Long > {


	WorkCategory findByCategoryNameIgnoreCase(String serviceType);



		
}

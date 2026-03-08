package com.booking.Repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.entity.WorkerDetails;

@Repository
public interface WorkerDetailsRepo extends JpaRepository<WorkerDetails,Long > {

	List<WorkerDetails> findByServiceTypeAndAvailability(String serviceType, Long id);



		
}

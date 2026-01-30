package com.apnakam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apnakam.entity.WorkerDetails;

@Repository
public interface WorkerDetailsRepo extends JpaRepository<WorkerDetails,Long > {

	   @Query("""
		        SELECT w.workerId 
		        FROM WorkerDetails w 
		        WHERE w.workerId LIKE %:year% 
		        ORDER BY w.workerId DESC
		    """)
		    List<String> findLastWorkerIdByYear(@Param("year") String year);

	Optional<WorkerDetails> findByWorkerId(String workerId);
		
}

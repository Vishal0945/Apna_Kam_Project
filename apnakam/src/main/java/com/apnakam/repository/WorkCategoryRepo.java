package com.apnakam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apnakam.entity.WorkCategory;
import com.apnakam.entity.WorkerDetails;

@Repository
public interface WorkCategoryRepo extends JpaRepository<WorkCategory,Long > {



		
}

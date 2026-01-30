package com.apnakam.serviceImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;

import com.apnakam.constant.CommonConstant;
import com.apnakam.entity.WorkerDetails;
import com.apnakam.model.ResponseModel;
import com.apnakam.model.WorkerDetailsModel;
import com.apnakam.service.WorkerService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
@Slf4j
@Service
public class WorkerServiceImpl extends AbstractMasterRepository implements WorkerService {
//	@Value("${msg91.authkey}")
//	private String authKey;
//
//	@Value("${msg91.sender}")
//	private String senderId;
	@Autowired
	private AllMethodsUses allMethodsUses;
	CommonConstant commonConstant = new CommonConstant();

	@Override
	public ResponseModel saveWorker(WorkerDetailsModel workerModel) {

		ResponseModel response = new ResponseModel();

		// Map Model → Entity
		WorkerDetails worker = new WorkerDetails();
		worker.setFullName(workerModel.getFullName());
		worker.setMobileNo(workerModel.getMobileNo());
		worker.setEmail(workerModel.getEmail());
		worker.setServiceType(workerModel.getServiceType());
		worker.setExperienceYears(workerModel.getExperienceYears());
		worker.setBasePrice(workerModel.getBasePrice());
		worker.setCity(workerModel.getCity());
		worker.setWorkerId(allMethodsUses.generateWorkerId());

		// System-controlled fields
		worker.setAvailability(true);
		worker.setRating(0.0);
		worker.setStatus("ACTIVE");

		Timestamp now = new Timestamp(System.currentTimeMillis());
		worker.setCreatedAt(now);
		worker.setUpdatedAt(now);

		WorkerDetails savedWorker = workerDetailsRepo.save(worker);

		response.setMessage("Worker added successfully");
		response.setData(savedWorker);
		response.setHttpStatus(HttpStatus.CREATED);
		response.setName("APNAKAM");

		return response;
	}

	@Override
	public ResponseModel getWorkerById(Long id) {

		ResponseModel response = new ResponseModel();

		Optional<WorkerDetails> optionalWorker = workerDetailsRepo.findById(id);

		if (optionalWorker.isEmpty()) {
			response.setHttpStatus(HttpStatus.NOT_FOUND);
			response.setMessage("Worker not found with id: " + id);
			response.setData(null);
			response.setName("GET_WORKER_BY_ID");
			return response;
		}

		WorkerDetails worker = optionalWorker.get();

		// Entity → Model (DTO)
		WorkerDetailsModel model = new WorkerDetailsModel();
		model.setId(worker.getId());
		model.setFullName(worker.getFullName());
		model.setMobileNo(worker.getMobileNo());
		model.setEmail(worker.getEmail());
		model.setServiceType(worker.getServiceType());
		model.setExperienceYears(worker.getExperienceYears());
		model.setBasePrice(worker.getBasePrice());
		model.setAvailability(worker.getAvailability());
		model.setRating(worker.getRating());
		model.setCity(worker.getCity());
		model.setStatus(worker.getStatus());

		response.setHttpStatus(HttpStatus.OK);
		response.setMessage("Worker fetched successfully");
		response.setData(model);
		response.setName("GET_WORKER_BY_ID");

		return response;
	}

	@Override
	public ResponseModel getAllWorkers(int page, int size) {

		ResponseModel response = new ResponseModel();

		Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
		Page<WorkerDetails> workerPage = workerDetailsRepo.findAll(pageable);

		if (workerPage.isEmpty()) {
			response.setHttpStatus(HttpStatus.NO_CONTENT);
			response.setMessage("No workers found");
			response.setData(Collections.emptyList());
			response.setName("GET_ALL_WORKERS");
			return response;
		}

		// Entity → DTO
		List<WorkerDetailsModel> workerModels = workerPage.getContent().stream().map(worker -> {
			WorkerDetailsModel model = new WorkerDetailsModel();
			model.setId(worker.getId());
			model.setFullName(worker.getFullName());
			model.setMobileNo(worker.getMobileNo());
			model.setServiceType(worker.getServiceType());
			model.setCity(worker.getCity());
			return model; // ✅ IMPORTANT
		}).collect(Collectors.toList());

		// Pagination metadata
		Map<String, Object> result = new HashMap<>();
		result.put("workers", workerModels);
		result.put("currentPage", workerPage.getNumber());
		result.put("totalItems", workerPage.getTotalElements());
		result.put("totalPages", workerPage.getTotalPages());
		result.put("pageSize", workerPage.getSize());

		response.setHttpStatus(HttpStatus.OK);
		response.setMessage("Workers fetched successfully");
		response.setData(result);
		response.setName("GET_ALL_WORKERS");

		return response;
	}
	
	
	@Override
	public ResponseModel updateWorker(String workerId, WorkerDetailsModel model) {

	    ResponseModel response = new ResponseModel();

	    log.info("INFO: Updating Worker with ID: " + workerId);

	    Optional<WorkerDetails> optionalWorker = workerDetailsRepo.findByWorkerId(workerId);

	    if (optionalWorker.isEmpty()) {
	        log.info("WARN: Worker not found with ID: " + workerId);
	        response.setHttpStatus(HttpStatus.NOT_FOUND);
	        response.setMessage("Worker not found with ID: " + workerId);
	        response.setData(null);
	        response.setName("UPDATE_WORKER");
	        return response;
	    }

	    WorkerDetails worker = optionalWorker.get();

	    // Update only fields from model
	    worker.setFullName(model.getFullName());
	    worker.setMobileNo(model.getMobileNo());
	    worker.setEmail(model.getEmail());
	    worker.setServiceType(model.getServiceType());
	    worker.setExperienceYears(model.getExperienceYears());
	    worker.setBasePrice(model.getBasePrice());
	    worker.setCity(model.getCity());

	    worker.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

	    WorkerDetails updatedWorker = workerDetailsRepo.save(worker);

	    // Map to DTO for response
	    WorkerDetailsModel responseModel = new WorkerDetailsModel();
	    responseModel.setFullName(updatedWorker.getFullName());
	    responseModel.setMobileNo(updatedWorker.getMobileNo());
	    responseModel.setEmail(updatedWorker.getEmail());
	    responseModel.setServiceType(updatedWorker.getServiceType());
	    responseModel.setExperienceYears(updatedWorker.getExperienceYears());
	    responseModel.setBasePrice(updatedWorker.getBasePrice());
	    responseModel.setCity(updatedWorker.getCity());

	    response.setHttpStatus(HttpStatus.OK);
	    response.setMessage("Worker updated successfully");
	    response.setData(responseModel);
	    response.setName("UPDATE_WORKER");

	    log.info("INFO: Worker updated successfully with ID: " + workerId);

	    return response;
	}

	@Override
	public ResponseModel deleteWorker(String workerId) {

	    ResponseModel response = new ResponseModel();

	    log.info("INFO: Soft deleting Worker ID: " + workerId);

	    Optional<WorkerDetails> optionalWorker = workerDetailsRepo.findByWorkerId(workerId);

	    if (optionalWorker.isEmpty()) {
	        log.info("WARN: Worker not found with ID: " + workerId);
	        response.setHttpStatus(HttpStatus.NOT_FOUND);
	        response.setMessage("Worker not found with ID: " + workerId);
	        response.setData(null);
	        response.setName("DELETE_WORKER");
	        return response;
	    }

	    WorkerDetails worker = optionalWorker.get();

	    // Soft delete
	    worker.setStatus("INACTIVE");
	    worker.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
	    workerDetailsRepo.save(worker);

	    response.setHttpStatus(HttpStatus.OK);
	    response.setMessage("Worker deleted successfully (soft delete)");
	    response.setData(null);
	    response.setName("DELETE_WORKER");

	    log.info("INFO: Worker soft-deleted successfully: " + workerId);

	    return response;
	}

}

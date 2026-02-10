package com.apnakam.service;

import com.apnakam.entity.WorkerDetails;
import com.apnakam.model.ResponseModel;
import com.apnakam.model.WorkerDetailsModel;

public interface WorkerService {

	ResponseModel saveWorker(WorkerDetailsModel  workerDetails);

	ResponseModel getWorkerById(String id);

	ResponseModel getAllWorkers(int page, int size, String ServiceTYpe);

	ResponseModel updateWorker(String workerId, WorkerDetailsModel model);

	ResponseModel deleteWorker(String workerId);

	ResponseModel getAllCategories();

	ResponseModel getserviceType(Integer id);



}

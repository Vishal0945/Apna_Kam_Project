package com.apnakam.controller;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apnakam.model.ResponseModel;
import com.apnakam.model.WorkerDetailsModel;
import com.apnakam.service.WorkerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
//@CrossOrigin
@RequestMapping("/Worker")
public class WorkerController {
	@Autowired
	WorkerService workerService;

	@GetMapping("/hello")
	public String hello() {
		return "api is running properly...";
	}

	@PostMapping("/add/worker")
	public ResponseModel addWorker(@RequestBody WorkerDetailsModel  workerDetails) {

		ResponseModel response = workerService.saveWorker(workerDetails);

		return response;
	}
	
	
	 @GetMapping("worker/{id}")
	    public ResponseModel getWorkerById(@PathVariable Long id) {
	        ResponseModel res = workerService.getWorkerById(id);
	        return res;
	        }

	 @GetMapping("workerAll")
	 public ResponseModel getAllWorkers(
	         @RequestParam(defaultValue = "0") int page,
	         @RequestParam(defaultValue = "10") int size) {

	     return workerService.getAllWorkers(page, size);
	 }

	    
//
	 @PutMapping("/update/{workerId}")
	 public ResponseModel updateWorker(
	         @PathVariable String workerId,
	         @RequestBody WorkerDetailsModel model) {

	    log.info("INFO: Update request received for Worker ID: " + workerId);
	     ResponseModel res = workerService.updateWorker(workerId, model);
	     log.info("INFO: Update request processed for Worker ID: " + workerId);
	     return res;
	 }

	 @DeleteMapping("/delete/{workerId}")
	 public ResponseModel deleteWorker(@PathVariable String workerId) {
	    log.info("INFO: Delete request received for Worker ID: " + workerId);
	     ResponseModel res = workerService.deleteWorker(workerId);
	    log.info("INFO: Delete request processed for Worker ID: " + workerId);
	     return res;
	 }

//
//	    @GetMapping("/city/{city}")
//	    public ResponseEntity<ResponseModel> getByCity(@PathVariable String city) {
//	        ResponseModel res = service.getWorkersByCity(city);
//	        return new ResponseEntity<>(res,
//	                (org.springframework.http.HttpStatus) res.getHttpStatus());
//	    }
//
//	    @GetMapping("/service/{type}")
//	    public ResponseEntity<ResponseModel> getByService(@PathVariable String type) {
//	        ResponseModel res = service.getWorkersByService(type);
//	        return new ResponseEntity<>(res,
//	                (org.springframework.http.HttpStatus) res.getHttpStatus());
//	    }
//
//	    @GetMapping("/search")
//	    public ResponseEntity<ResponseModel> search(
//	            @RequestParam String city,
//	            @RequestParam String service) {
//	        ResponseModel res = service.searchWorkers(city, service);
//	        return new ResponseEntity<>(res,
//	                (org.springframework.http.HttpStatus) res.getHttpStatus());
//	    }

}


	
	

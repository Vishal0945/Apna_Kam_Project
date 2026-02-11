package com.apnakam.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.apnakam.model.ResponseModel;
import com.apnakam.serviceImpl.ResourceNotFoundException;


	




	@RestControllerAdvice
	public class GlobalExceptionHandler {

	    @ExceptionHandler(WorkerIdGenerationException.class)
	    public ResponseEntity<ResponseModel> handleWorkerIdException(
	            WorkerIdGenerationException ex) {

	        ResponseModel response = new ResponseModel();
	        response.setMessage(ex.getMessage());
	        response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
	        response.setName("APNAKAM");

	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	

	    @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<ResponseModel> handleNotFound(ResourceNotFoundException ex) {

	        ResponseModel response = new ResponseModel();
	        response.setMessage(ex.getMessage());
	        response.setHttpStatus(HttpStatus.NOT_FOUND);
	        response.setName("ERROR");

	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<ResponseModel> handleGlobal(Exception ex) {

	        ResponseModel response = new ResponseModel();
	        response.setMessage("Something went wrong");
	        response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
	        response.setName("ERROR");

	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
}

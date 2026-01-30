package com.apnakam.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.apnakam.model.ResponseModel;


	




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
	

}

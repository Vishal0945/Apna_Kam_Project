package com.apnakam.exception;

public class WorkerIdGenerationException extends RuntimeException {
	 public WorkerIdGenerationException(String message) {
	        super(message);
	    }

	    public WorkerIdGenerationException(String message, Throwable cause) {
	        super(message, cause);
	    }
}

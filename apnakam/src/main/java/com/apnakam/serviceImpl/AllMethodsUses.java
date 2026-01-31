package com.apnakam.serviceImpl;

import java.time.Year;
import java.util.List;

import org.springframework.stereotype.Component;

import com.apnakam.exception.WorkerIdGenerationException;

@Component
public class AllMethodsUses extends AbstractMasterRepository{
	
	 public String generateWorkerId() {

	        try { String prefix = "WK";
	        String year = String.valueOf(Year.now().getValue());

	        List<String> lastIds = workerDetailsRepo.findLastWorkerId();

	        int nextSequence = 1;

	        if (lastIds != null && !lastIds.isEmpty()) {
	            String lastId = lastIds.get(0); // e.g. WK/2026/0001

	            if (!lastId.matches("WK/\\d{4}/\\d{4}")) {
	                throw new WorkerIdGenerationException(
	                    "Invalid workerId format found in DB: " + lastId
	                );
	            }

	            String lastSequence = lastId.substring(lastId.lastIndexOf("/") + 1);
	            nextSequence = Integer.parseInt(lastSequence) + 1;
	        }

	        return String.format("%s/%s/%04d", prefix, year, nextSequence);
	        } catch (NumberFormatException e) {
	        	
	            throw new WorkerIdGenerationException("Error parsing workerId sequence", e);

	        } catch (Exception e) {
	            throw new WorkerIdGenerationException("Failed to generate Worker ID", e);
	        }
	    }

}

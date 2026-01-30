package com.apnakam.serviceImpl;

import java.time.Year;
import java.util.List;

import com.apnakam.exception.WorkerIdGenerationException;


public class AllMethodsUses extends AbstractMasterRepository{
	
	 public String generateWorkerId() {

	        try {
	            String prefix = "WK";
	            String year = String.valueOf(Year.now().getValue());

	            List<String> lastIds = workerDetailsRepo.findLastWorkerIdByYear(year);

	            int nextSequence = 1;

	            if (lastIds != null && !lastIds.isEmpty()) {
	                String lastId = lastIds.get(0);

	                if (lastId == null || !lastId.matches("WK/\\d{4}/\\d+")) {
	                    throw new WorkerIdGenerationException(
	                        "Invalid workerId format found in DB: " + lastId
	                    );
	                }

	                String seq = lastId.substring(lastId.lastIndexOf("/") + 1);
	                nextSequence = Integer.parseInt(seq) + 1;
	            }

	            return String.format("%s/%s/%04d", prefix, year, nextSequence);

	        } catch (NumberFormatException e) {
	            throw new WorkerIdGenerationException("Error parsing workerId sequence", e);

	        } catch (Exception e) {
	            throw new WorkerIdGenerationException("Failed to generate Worker ID", e);
	        }
	    }

}

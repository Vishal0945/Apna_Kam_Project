package com.apnakam.service;

import java.util.List;

import com.apnakam.controller.AddressController;
import com.apnakam.entity.WorkerDetails;
import com.apnakam.model.AddressRequestDTO;
import com.apnakam.model.AddressResponseDTO;
import com.apnakam.model.ResponseModel;
import com.apnakam.model.WorkerDetailsModel;

import jakarta.validation.Valid;

public interface AddressService {

	   AddressResponseDTO save(AddressRequestDTO dto);
	    List<AddressResponseDTO> getAll(String mobileNumber);
	    void delete(Long addressId,String mobileNumber);
	    void setDefault(Long addressId);






}

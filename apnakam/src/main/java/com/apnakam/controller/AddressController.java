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
import org.springframework.http.*;

import com.apnakam.model.AddressRequestDTO;
import com.apnakam.model.ResponseModel;
import com.apnakam.service.AddressService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
//@CrossOrigin
@RequestMapping("/api/v1/addresses")
public class AddressController {
	@Autowired
	 AddressService service;

	@GetMapping("/hello")
	public String hello() {
		return "api is running properly...";
	}
	  @PostMapping("/SaveAddress")
	    public ResponseModel save(@Valid @RequestBody AddressRequestDTO dto) {

	        ResponseModel response = new ResponseModel();
	        response.setData(service.save(dto));
	        response.setMessage("Address saved successfully");
	        response.setHttpStatus(HttpStatus.CREATED);
	        response.setName("SAVE_ADDRESS");

	        return response;
	    }

	    @GetMapping("/getAllAddress/History")
	    public ResponseEntity<ResponseModel> list(@RequestParam String mobileNumber) {

	        ResponseModel response = new ResponseModel();
	        response.setData(service.getAll(mobileNumber));
	        response.setMessage("Address list fetched successfully");
	        response.setHttpStatus(HttpStatus.OK);
	        response.setName("LIST_ADDRESS");

	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    @DeleteMapping("/delete/address")
	    public ResponseEntity<ResponseModel> delete(@RequestParam Long id , @RequestParam String mobileNumber) {

	        service.delete(id, mobileNumber);

	        ResponseModel response = new ResponseModel();
	        response.setMessage("Address deleted successfully");
	        response.setHttpStatus(HttpStatus.OK);
	        response.setName("DELETE_ADDRESS");

	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    @PutMapping("/{id}/default")
	    public ResponseEntity<ResponseModel> setDefault(@PathVariable Long id) {

	        service.setDefault(id);

	        ResponseModel response = new ResponseModel();
	        response.setMessage("Default address updated successfully");
	        response.setHttpStatus(HttpStatus.OK);
	        response.setName("SET_DEFAULT_ADDRESS");

	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
}


	
	

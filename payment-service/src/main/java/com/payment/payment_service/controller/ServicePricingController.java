package com.payment.payment_service.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.payment.payment_service.dto.ResponseModel;
import com.payment.payment_service.dto.ServicePricingModel;
import com.payment.payment_service.service.ServicePricingService;



@RestController
@RequestMapping("/service/pricing")
public class ServicePricingController {

    @Autowired
    private ServicePricingService servicePricingService;
    
    @GetMapping("/hello")
    public String test(){

        return "Hello JaVa";
    }

    @PostMapping("/add")
    public ResponseModel addService(@RequestBody ServicePricingModel model){

        return servicePricingService.addServicePricing(model);
    }


    @GetMapping("/all")
    public ResponseModel getAllServices(){

        return servicePricingService.getAllServices();
    }

   
    @PutMapping("/update")
    public ResponseModel updateService(
            @RequestParam Long id,
            @RequestBody ServicePricingModel model){

        return servicePricingService.updateServicePricing(id, model);
    }

    @PutMapping("/disable/{id}")
    public ResponseModel disableService(@PathVariable Long id){

        return servicePricingService.disableService(id);
    }

}
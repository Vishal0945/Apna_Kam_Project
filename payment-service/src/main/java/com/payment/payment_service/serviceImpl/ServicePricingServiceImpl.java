package com.payment.payment_service.serviceImpl;

import java.util.List;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.payment_service.CommonConstant.CommonConstant;
import com.payment.payment_service.dto.ResponseModel;
import com.payment.payment_service.dto.ServicePricingModel;
import com.payment.payment_service.entity.ServicePricing;
import com.payment.payment_service.entity.ServiceType;
import com.payment.payment_service.entity.WorkCategory;
import com.payment.payment_service.repository.ServicePricingRepository;
import com.payment.payment_service.service.ServicePricingService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class ServicePricingServiceImpl extends AbstractMasterRepository implements ServicePricingService {


    
	CommonConstant commonConstant =new CommonConstant(); 


//    @Override
//    public ResponseModel addServicePricing(ServicePricingModel model) {
//
//        ResponseModel res = new ResponseModel();
//
//        ServicePricing entity = new ServicePricing();
//
//        entity.setServiceType(model.getServiceType());
//        entity.setBasePrice(model.getBasePrice());
//        entity.setActive(model.getActive());
//        entity.setServiceSubType(model.getServiceSubType());
//        entity.setCommissionPercent(commonConstant.COMMISSION_PERCENT);
//        servicePricingRepository.save(entity);
//        WorkCategory saveWork=null;
//        int catId=0;
//        WorkCategory checkWorkCatPresent= workCategoryRepo.findByCategoryName(model.getServiceType());
//       ServiceType checkServiceTypePresent =serviceTypeRepo.findByServiceName(model.getServiceSubType());
//        
//if (checkWorkCatPresent!=null) {
//	
//}
//else {
//	WorkCategory newWork= new WorkCategory();
//	newWork.setCategoryName(model.getServiceType());
//	saveWork=workCategoryRepo.save(newWork);
//	
//}
//
//if (checkServiceTypePresent!=null) {
//	
//} else {
//	ServiceType newServiceType = new ServiceType();
//	newServiceType.setServiceName(model.getServiceSubType());
//	if (checkWorkCatPresent!=null) {
//		catId=saveWork.getCategoryId();
//		newServiceType.setCategoryId(catId);
//	} else {
//		catId=	checkWorkCatPresent.getCategoryId();
//		newServiceType.setCategoryId(catId);
//	}
//	
//			
//}
//
//
//
//        res.setMessage("Service pricing added successfully");
//        res.setData(entity);
//
//        return res;
//    }
	
	
	 @Override
	    @Transactional
	    public ResponseModel addServicePricing(ServicePricingModel model) {

	        ResponseModel res = new ResponseModel();

	        try {

	            log.info("Adding service pricing for serviceType: {}", model.getServiceType());

	            ServicePricing entity = new ServicePricing();

	            entity.setServiceType(model.getServiceType());
	            entity.setBasePrice(model.getBasePrice());
	            entity.setActive(model.getActive());
	            entity.setServiceSubType(model.getServiceSubType());
	            entity.setCommissionPercent(commonConstant.COMMISSION_PERCENT);

	            servicePricingRepository.save(entity);

	            log.info("ServicePricing saved successfully");

	            int catId = 0;

	            WorkCategory workCategory = workCategoryRepo.findByCategoryNameIgnoreCase(model.getServiceType());
	            ServiceType serviceType = serviceTypeRepo.findByServiceNameIgnoreCase(model.getServiceSubType());

	            // ---------- Handle WorkCategory ----------
	            if (workCategory == null) {

	                log.info("WorkCategory not found. Creating new category for {}", model.getServiceType());

	                WorkCategory newWork = new WorkCategory();
	                newWork.setCategoryName(model.getServiceType());

	                WorkCategory savedWork = workCategoryRepo.save(newWork);

	                catId = savedWork.getCategoryId();

	                log.info("New WorkCategory created with id {}", catId);

	            } else {

	                catId = workCategory.getCategoryId();
	                log.info("Existing WorkCategory found with id {}", catId);
	            }

	            // ---------- Handle ServiceType ----------
	            if (serviceType == null) {

	                log.info("ServiceType not found. Creating new ServiceType for {}", model.getServiceSubType());

	                ServiceType newServiceType = new ServiceType();
	                newServiceType.setServiceName(model.getServiceSubType());
	                newServiceType.setCategoryId(catId);

	                serviceTypeRepo.save(newServiceType);

	                log.info("New ServiceType created");

	            } else {
	                log.info("ServiceType already exists");
	            }
	            res.setHttpStatus(HttpStatus.SC_OK);
	            res.setMessage("Service pricing added successfully");
	            res.setData(entity);

	        } catch (Exception e) {

	            log.error("Error while adding service pricing", e);
	            res.setHttpStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);

	            res.setMessage("Failed to add service pricing");
	        }

	        return res;
	    }
	

    @Override
    public ResponseModel getAllServices() {

        ResponseModel res = new ResponseModel();

        List<ServicePricing> services = servicePricingRepository.findAll();

        res.setMessage("All services fetched");
        res.setData(services);

        return res;
    }

    @Override
    public ResponseModel updateServicePricing(Long id, ServicePricingModel model) {

        ResponseModel res = new ResponseModel();

        ServicePricing service =
                servicePricingRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Service not found"));

        service.setBasePrice(model.getBasePrice());
//        service.setServiceSubType(model.getServiceSubType());


        servicePricingRepository.save(service);
        
        res.setHttpStatus(HttpStatus.SC_OK);
        res.setMessage("Service pricing updated");
        res.setData(service);

        return res;
    }

    @Override
    public ResponseModel disableService(Long id) {

        ResponseModel res = new ResponseModel();

        ServicePricing service =
                servicePricingRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Service not found"));

        service.setActive(false);

        servicePricingRepository.save(service);

        res.setMessage("Service disabled");

        return res;
    }
}
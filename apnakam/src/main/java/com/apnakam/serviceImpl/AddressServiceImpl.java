package com.apnakam.serviceImpl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apnakam.constant.CommonConstant;
import com.apnakam.entity.Registration;
import com.apnakam.entity.UserAddress;
import com.apnakam.model.AddressRequestDTO;
import com.apnakam.model.AddressResponseDTO;
import com.apnakam.service.AddressService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class AddressServiceImpl extends AbstractMasterRepository implements AddressService {

	@Autowired
	private AllMethodsUses allMethodsUses;
	CommonConstant commonConstant = new CommonConstant();

	

    // Temporary - replace with actual logged-in user logic
    private Long getUserId() {
        return 1L; // Replace later with real user context
    }

    // ================= SAVE =================
    @Override
    public AddressResponseDTO save(AddressRequestDTO dto) {

        try {

            if (dto == null) {
                log.error("AddressRequestDTO is null");
                throw new IllegalArgumentException("Address request cannot be null");
            }

            if (dto.getRegMobileNumber() == null || dto.getRegMobileNumber().isBlank()) {
                log.error("Mobile number is missing in request");
                throw new IllegalArgumentException("Mobile number is required");
            }

            String number = dto.getRegMobileNumber();
            log.info("Saving address for mobile number: {}", number);

            // 🔹 Fetch registration
            Registration reg = registrationRepo.findByMobileNumber(number);

            if (reg == null) {
                log.error("User not found with mobile number: {}", number);
                throw new ResourceNotFoundException("User not found with given mobile number");
            }

            Long userId = reg.getRegistrationId();

            // 🔹 If default selected → reset old defaults
            if (Boolean.TRUE.equals(dto.getIsDefault())) {
                resetDefault(userId);
            }

            // 🔹 Convert DTO to Entity
            UserAddress address = mapper.toEntity(dto, userId);

            address.setUserId(userId);

            // 🔹 Save
            UserAddress saved = repository.save(address);

            log.info("Address saved successfully with id: {} for userId: {}", 
                     saved.getId(), userId);

            return mapper.toDto(saved);

        } catch (ResourceNotFoundException ex) {
            throw ex; // already handled in GlobalExceptionHandler

        } catch (IllegalArgumentException ex) {
            log.error("Validation error while saving address: {}", ex.getMessage());
            throw ex;

        } catch (Exception ex) {
            log.error("Unexpected error while saving address", ex);
            throw new RuntimeException("Failed to save address. Please try again.");
        }
    }


    // ================= GET ALL =================
    @Override
    public List<AddressResponseDTO> getAll() {

        Long userId = getUserId();
        log.info("Fetching addresses for userId: {}", userId);

        List<UserAddress> list =
                repository.findByUserIdAndIsActiveTrueOrderByIsDefaultDescCreatedAtDesc(userId);

        if (list.isEmpty()) {
            log.warn("No addresses found for userId: {}", userId);
        }

        return list.stream()
                .map(mapper::toDto)
                .toList();
    }

    // ================= DELETE (SOFT DELETE) =================
    @Override
    public void delete(Long addressId) {

        Long userId = getUserId();
        log.info("Deleting addressId: {} for userId: {}", addressId, userId);

        UserAddress address = repository
                .findByIdAndUserIdAndIsActiveTrue(addressId, userId)
                .orElseThrow(() -> {
                    log.error("Address not found with id: {}", addressId);
                    return new ResourceNotFoundException("Address not found");
                });

        address.setIsActive(false);
        address.setIsDefault(false);

        repository.save(address);

        log.info("Address soft deleted successfully: {}", addressId);
    }

    // ================= SET DEFAULT =================
    @Override
    public void setDefault(Long addressId) {

        Long userId = getUserId();
        log.info("Setting default addressId: {} for userId: {}", addressId, userId);

        UserAddress address = repository
                .findByIdAndUserIdAndIsActiveTrue(addressId, userId)
                .orElseThrow(() -> {
                    log.error("Address not found with id: {}", addressId);
                    return new ResourceNotFoundException("Address not found");
                });

        resetDefault(userId);

        address.setIsDefault(true);

        repository.save(address);

        log.info("Default address updated successfully: {}", addressId);
    }

    // ================= RESET DEFAULT =================
    private void resetDefault(Long userId) {

        List<UserAddress> addresses =
                repository.findByUserIdAndIsActiveTrueOrderByCreatedAtDesc(userId);

        for (UserAddress addr : addresses) {
            if (Boolean.TRUE.equals(addr.getIsDefault())) {
                addr.setIsDefault(false);
            }
        }

        repository.saveAll(addresses);

        log.debug("Previous default addresses reset for userId: {}", userId);
    }
	



}

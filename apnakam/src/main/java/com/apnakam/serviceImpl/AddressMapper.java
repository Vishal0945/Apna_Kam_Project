package com.apnakam.serviceImpl;

import org.springframework.stereotype.Component;

import com.apnakam.entity.UserAddress;
import com.apnakam.model.AddressRequestDTO;
import com.apnakam.model.AddressResponseDTO;

@Component
public class AddressMapper {

    public UserAddress toEntity(AddressRequestDTO dto, Long userId) {
        UserAddress address = new UserAddress();
        address.setUserId(userId);
        address.setAddressLine1(dto.getAddressLine1());
        address.setAddressLine2(dto.getAddressLine2());
        address.setLandmark(dto.getLandmark());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setPincode(dto.getPincode());
        address.setLatitude(dto.getLatitude());
        address.setFullName(dto.getFullName());
        address.setLongitude(dto.getLongitude());
        address.setMobileNumber(dto.getMobileNumber());
        address.setAddressType(dto.getAddressType());
        address.setIsDefault(Boolean.TRUE.equals(dto.getIsDefault()));
        return address;
    }

    public AddressResponseDTO toDto(UserAddress address) {
        return AddressResponseDTO.builder()
                .id(address.getId())
                .addressLine1(address.getAddressLine1())
                .addressLine2(address.getAddressLine2())
                .landmark(address.getLandmark())
                .city(address.getCity())
                .state(address.getState())
                .pincode(address.getPincode())
                .latitude(address.getLatitude())
                .longitude(address.getLongitude())
                .addressType(address.getAddressType())
                .isDefault(address.getIsDefault())
                .mobileNumber(address.getMobileNumber())
                .fullName(address.getFullName())
                .build();
    }
}


package com.propertymanagement.PropertyManagement.service;

import com.propertymanagement.PropertyManagement.dto.PropertyUnitDTO;
import com.propertymanagement.PropertyManagement.dto.RentPaymentOverviewDTO;
import com.propertymanagement.PropertyManagement.dto.propertyResponse.PropertyUnitResponseDTO;
import com.propertymanagement.PropertyManagement.entity.PropertyUnit;
import com.propertymanagement.PropertyManagement.entity.RentPayment;

import java.util.List;

public interface PropertyUnitService {
    // add property
    PropertyUnitResponseDTO addNewProperty(PropertyUnitDTO propertyUnitDTO);

    // update property
    PropertyUnitResponseDTO updateProperty(PropertyUnitDTO propertyUnitDTO, int propertyId);

    // archive property
    PropertyUnit archiveProperty(int propertyId, int tenantId);

    // delete property
    String deleteProperty(int id);

    // get all properties
    List<PropertyUnitResponseDTO> getAllProperties();

    // get property by id
    PropertyUnitResponseDTO getPropertyByPropertyId(int propertyId);

    // ge all occupied units

    List<PropertyUnitResponseDTO> fetchFilteredUnits(String tenantName, String rooms, String roomName, Boolean occupied);

    List<PropertyUnitResponseDTO> fetchAllUnoccupiedUnits(int rooms, String roomName);


}

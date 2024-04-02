package com.propertymanagement.PropertyManagement.service;

import com.propertymanagement.PropertyManagement.dto.PropertyUnitDTO;
import com.propertymanagement.PropertyManagement.dto.propertyResponse.PropertyUnitResponseDTO;

import java.util.List;

public interface PropertyUnitService {
    // add property
    PropertyUnitResponseDTO addNewProperty(PropertyUnitDTO propertyUnitDTO);

    // update property
    PropertyUnitResponseDTO updateProperty(PropertyUnitDTO propertyUnitDTO, int propertyId);

    // archive property
    PropertyUnitResponseDTO archiveProperty(int propertyId, int tenantId);

    // delete property
    String deleteProperty(int id);

    // get all properties
    List<PropertyUnitResponseDTO> getAllProperties();

    // get property by id
    PropertyUnitResponseDTO getPropertyByPropertyId(int propertyId);
}

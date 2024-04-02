package com.propertymanagement.PropertyManagement.controller;

import com.propertymanagement.PropertyManagement.dto.PropertyUnitDTO;
import com.propertymanagement.PropertyManagement.dto.Response;
import com.propertymanagement.PropertyManagement.entity.PropertyUnit;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PropertyUnitController {
    // add property
    ResponseEntity<Response> addNewProperty(PropertyUnitDTO propertyUnitDTO);

    // update property
    ResponseEntity<Response> updateProperty(PropertyUnitDTO propertyUnitDTO, int propertyId);

    // archive property
    ResponseEntity<Response> archiveProperty(int propertyId, int tenantId);

    // delete property
    String deleteProperty(int id);

    // get all properties
    ResponseEntity<Response> getAllProperties();

    // get property by id
    ResponseEntity<Response> getPropertyByPropertyId(int propertyId);
}

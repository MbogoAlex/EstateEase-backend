package com.propertymanagement.PropertyManagement.controller;

import com.propertymanagement.PropertyManagement.dto.PropertyUnitDTO;
import com.propertymanagement.PropertyManagement.dto.Response;
import com.propertymanagement.PropertyManagement.entity.PropertyUnit;
import com.propertymanagement.PropertyManagement.service.PropertyUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Map.of;

@RestController
@RequestMapping("/api")
public class PropertyUnitControllerImpl implements PropertyUnitController {
    private PropertyUnitService propertyUnitService;
    @Autowired
    public PropertyUnitControllerImpl(PropertyUnitService propertyUnitService) {
        this.propertyUnitService = propertyUnitService;
    }
    @Override
    @PostMapping("/propertyunit")
    public ResponseEntity<Response> addNewProperty(@RequestBody PropertyUnitDTO propertyUnitDTO) {
        return buildResponse("property", propertyUnitService.addNewProperty(propertyUnitDTO), "Created successfully", HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/propertyunit/propertyId={id}")
    public ResponseEntity<Response> updateProperty(@RequestBody PropertyUnitDTO propertyUnitDTO, @PathVariable int propertyId) {
        return buildResponse("property", propertyUnitService.updateProperty(propertyUnitDTO, propertyId), "Updated successfully", HttpStatus.OK);
    }

    @Override
    @PutMapping("/propertyunit/archive/propertyId={propertyId}/tenantId={tenantId}")
    public ResponseEntity<Response> archiveProperty(@PathVariable("propertyId") int propertyId, @PathVariable("tenantId") int tenantId) {
        return buildResponse("property", propertyUnitService.archiveProperty(propertyId, tenantId), "Archived successfully", HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/propertyunit/propertyId={propertyId}")
    public String deleteProperty(@PathVariable("propertyId") int id) {
        return propertyUnitService.deleteProperty(id);
    }

    @Override
    @GetMapping("/propertyunit")
    public ResponseEntity<Response> getAllProperties() {
        return buildResponse("property", propertyUnitService.getAllProperties(), "Fetched successfully", HttpStatus.OK);
    }

    @Override
    @GetMapping("/propertyunit/propertyId={propertyId}")
    public ResponseEntity<Response> getPropertyByPropertyId(@PathVariable("propertyId") int propertyId) {
        return buildResponse("property", propertyUnitService.getPropertyByPropertyId(propertyId), "Fetched successfully", HttpStatus.OK);
    }
    @GetMapping("/propertyunit/occupied")
    @Override
    public ResponseEntity<Response> fetchAllOccupiedUnits() {
        return  buildResponse("property", propertyUnitService.fetchAllOccupiedUnits(), "Fetched successfully", HttpStatus.OK);
    }

    private ResponseEntity<Response> buildResponse(String desc, Object data, String message, HttpStatus status) {
        return ResponseEntity.status(status)
                .body(Response.builder()
                        .timestamp(LocalDateTime.now())
                        .data(data == null ? null : of(desc, data))
                        .message(message)
                        .status(status)
                        .statusCode(status.value())
                        .build());
    }
}

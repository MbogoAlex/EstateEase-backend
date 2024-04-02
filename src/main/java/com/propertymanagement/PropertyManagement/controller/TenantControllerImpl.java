package com.propertymanagement.PropertyManagement.controller;

import com.propertymanagement.PropertyManagement.dto.*;
import com.propertymanagement.PropertyManagement.entity.Tenant;
import com.propertymanagement.PropertyManagement.service.TenantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static java.util.Map.of;

@RestController
@RequestMapping("/api")
public class TenantControllerImpl implements TenantController{
    private TenantService tenantService;

    public TenantControllerImpl(TenantService tenantService) {
        this.tenantService = tenantService;
    }
    @Override
    @PostMapping("/tenant")
    public ResponseEntity<Response> addNewTenant(@RequestBody TenantDTO tenantDTO) {
        return buildResponse("tenant", tenantService.addNewTenant(tenantDTO), "Created successfully", HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/tenant")
    public ResponseEntity<Response> updateTenant(@RequestBody TenantUpdateDTO tenantDTO) {
        return buildResponse("tenant", tenantService.updateTenant(tenantDTO), "Updated successfully", HttpStatus.OK);
    }

    @Override
    @GetMapping("/tenant")
    public ResponseEntity<Response> getAllTenants() {
        return buildResponse("tenant", tenantService.getAllTenants(), "Fetched successfully", HttpStatus.OK);
    }

    @Override
    @GetMapping("/tenant/tenantId={tenantId}")
    public ResponseEntity<Response> getTenantByTenantId(@PathVariable("tenantId") int tenantId) {
        return buildResponse("tenant", tenantService.getTenantByTenantId(tenantId), "Fetched successfully", HttpStatus.OK);
    }

    @Override
    @PostMapping("/rentpayment/newrow")
    public ResponseEntity<Response> addNewRentPaymentRow(@RequestBody RentPaymentDTO rentPaymentDTO) {
        return buildResponse("rentPayment", tenantService.addNewRentPaymentRow(rentPaymentDTO ), "Created successfully", HttpStatus.OK);
    }


    @Override
    @PostMapping("/rentpayment/rentPaymentTblId={id}")
    public ResponseEntity<Response> payRent(@RequestBody RentPaymentRequestDTO rentPaymentRequestDTO, @PathVariable("id") int rentPaymentTblId) {
        return buildResponse("rentPayment", tenantService.payRent(rentPaymentRequestDTO, rentPaymentTblId), "Paid successfully", HttpStatus.OK);
    }

    @Override
    @PutMapping("/tenant/archive/tenantId={tenantId}/propertyId={propertyId}")
    public ResponseEntity<Response> archiveTenant(@PathVariable("tenantId") int tenantId, @PathVariable("propertyId") int propertyId) {
        return buildResponse("tenant", tenantService.archiveTenant(tenantId, propertyId), "Archived successfully", HttpStatus.OK);
    }

    @Override
    @PostMapping("/tenant/login")
    public ResponseEntity<Response> tenantLogin(@RequestBody TenantLoginDTO tenantLoginDTO) {
        return buildResponse("tenant", tenantService.tenantLogin(tenantLoginDTO), "Login successful", HttpStatus.OK);
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

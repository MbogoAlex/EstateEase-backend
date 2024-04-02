package com.propertymanagement.PropertyManagement.controller;

import com.propertymanagement.PropertyManagement.dto.*;
import com.propertymanagement.PropertyManagement.entity.RentPayment;
import com.propertymanagement.PropertyManagement.entity.Tenant;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TenantController {
    // add new tenant
    ResponseEntity<Response> addNewTenant(TenantDTO tenantDTO);

    // update tenant
    ResponseEntity<Response> updateTenant(TenantUpdateDTO tenantDTO);

    // get all tenants

    ResponseEntity<Response> getAllTenants();

    // getTenantByTenantId

    ResponseEntity<Response> getTenantByTenantId(int tenantId);

    // add new rent payment row
    ResponseEntity<Response> addNewRentPaymentRow(RentPaymentDTO rentPaymentDTO);

    // update rent payment

    ResponseEntity<Response> payRent(RentPaymentRequestDTO rentPaymentRequestDTO, int rentPaymentTblId);

    // archive tenant
    ResponseEntity<Response> archiveTenant(int tenantId, int propertyId);
    ResponseEntity<Response> tenantLogin(TenantLoginDTO tenantLoginDTO);
}

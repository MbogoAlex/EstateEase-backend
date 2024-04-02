package com.propertymanagement.PropertyManagement.service;

import com.propertymanagement.PropertyManagement.dto.RentPaymentDTO;
import com.propertymanagement.PropertyManagement.dto.TenantDTO;
import com.propertymanagement.PropertyManagement.dto.TenantLoginDTO;
import com.propertymanagement.PropertyManagement.dto.tenantResponse.TenantResponseDTO;
import com.propertymanagement.PropertyManagement.entity.RentPayment;
import com.propertymanagement.PropertyManagement.entity.Tenant;
import java.util.List;

public interface TenantService {
    // add new tenant
    TenantResponseDTO addNewTenant(TenantDTO tenantDTO);

    // update tenant
    TenantResponseDTO updateTenant(TenantDTO tenantDTO);

    // get all tenants
    List<TenantResponseDTO> getAllTenants();

    // get tenant by tenant id

    TenantResponseDTO getTenantByTenantId(int tenantId);

    // add new rent payment row
    RentPayment addNewRentPaymentRow(RentPaymentDTO rentPaymentDTO);

    // update rent payment

    RentPayment payRent(RentPaymentDTO rentPaymentDTO, int rentPaymentTblId);

    // archive tenant
    TenantResponseDTO archiveTenant(int tenantId, int propertyId);

    TenantResponseDTO tenantLogin(TenantLoginDTO tenantLoginDTO);


}

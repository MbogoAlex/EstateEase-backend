package com.propertymanagement.PropertyManagement.service;

import com.propertymanagement.PropertyManagement.dto.*;
import com.propertymanagement.PropertyManagement.dto.tenantResponse.TenantResponseDTO;
import com.propertymanagement.PropertyManagement.entity.RentPayment;
import com.propertymanagement.PropertyManagement.entity.Tenant;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public interface TenantService {
    // add new tenant
    TenantResponseDTO addNewTenant(TenantDTO tenantDTO);

    // update tenant
    TenantResponseDTO updateTenant(TenantUpdateDTO tenantUpdateDTO);

    // get all tenants
    List<TenantResponseDTO> getAllTenants();

    // get tenant by tenant id

    TenantResponseDTO getTenantByTenantId(int tenantId);

    // add new rent payment row
    RentPayment addNewRentPaymentRow(RentPaymentDTO rentPaymentDTO);

    // update rent payment

    RentPaymentDetailsDTO payRent(RentPaymentRequestDTO rentPaymentRequestDTO, int rentPaymentTblId) throws URISyntaxException, IOException, InterruptedException;

    // archive tenant
    TenantResponseDTO archiveTenant(int tenantId, int propertyId);

    TenantResponseDTO tenantLogin(TenantLoginDTO tenantLoginDTO);

    RentPaymentDetailsDTO activateLatePaymentPenaltyForSingleTenant(RentPenaltyDTO rentPenaltyDTO, int rentPaymentTblId);

    RentPaymentDetailsDTO deActivateLatePaymentPenaltyForSingleTenant(int rentPaymentTblId);

    List<RentPaymentDetailsDTO> activateLatePaymentPenaltyForMultipleTenants(RentPenaltyDTO rentPenaltyDTO, String month, String year);

    List<RentPaymentDetailsDTO> deActivateLatePaymentPenaltyForMultipleTenants(String month, String year);

    List<DetailedRentPaymentInfoDTO> getRentPaymentRowsByTenantId(Integer tenantId, String month, Integer year, String roomName, String rooms, String tenantName, Boolean rentPaymentStatus, Boolean paidLate, Boolean tenantActive);

    ByteArrayOutputStream generateRentPaymentsReport(Integer tenantId, String month, Integer year, String roomName, String rooms, String tenantName, Boolean rentPaymentStatus, Boolean paidLate, Boolean tenantActive) throws JRException;
    ResponseEntity<?> handleCallback(Map<String, Object> mpesaResponse);

    RentPaymentDetailsDTO checkPaymentStatus(int id);
}

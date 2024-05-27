package com.propertymanagement.PropertyManagement.dao;

import com.propertymanagement.PropertyManagement.dto.DetailedRentPaymentInfoDTO;
import com.propertymanagement.PropertyManagement.dto.RentPaymentDetailsDTO;
import com.propertymanagement.PropertyManagement.entity.RentPayment;
import com.propertymanagement.PropertyManagement.entity.Tenant;
import java.util.List;

public interface TenantDao {
    // add new tenant
    Tenant addNewTenant(Tenant tenant);

    // update tenant
    Tenant updateTenant(Tenant tenant);

    // add new rent payment row
    RentPayment addNewRentPaymentRow(RentPayment rentPayment);

    // update rent payment

    RentPayment payRent(RentPayment rentPayment);

    // archive tenant
    Tenant archiveTenant(Tenant tenant);

    // get all tenants
    List<Tenant> getAllTenants();

    // get tenant by id
    Tenant getTenantByTenantId(int tenantId);

    // get rent payment row
    RentPayment getRentPaymentRow(int rentPaymentTblId);

    // fetch tenant by password
    Tenant fetchTenantByPhoneNumberAndPassword(String phoneNumber, String password);

    List<Tenant> getActiveTenants();

    List<RentPayment> getRentPaymentRows(String month, String year);

    RentPayment getSingleRentPaymentRow(int rentPaymentTblId);

    RentPayment updateRentPaymentRow(RentPayment rentPayment);

    List<DetailedRentPaymentInfoDTO> getRentPaymentRowsByTenantId(Integer tenantId, String month, Integer year, String roomName, Integer rooms, String tenantName, Boolean rentPaymentStatus, Boolean paidLate, Boolean tenantActive);
}

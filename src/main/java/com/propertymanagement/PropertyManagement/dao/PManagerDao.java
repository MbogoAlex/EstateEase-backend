package com.propertymanagement.PropertyManagement.dao;

import com.propertymanagement.PropertyManagement.dto.DetailedRentPaymentInfoDTO;
import com.propertymanagement.PropertyManagement.dto.PManagerLoginDTO;
import com.propertymanagement.PropertyManagement.entity.*;

import java.util.List;

public interface PManagerDao {

    PManager addNewPManager(PManager pManager);

    PManager updatePmanager(PManager pManager);

    PManager archivePManager(PManager pManager);

    PropertyUnit addNewUnit(PropertyUnit propertyUnit);
    Tenant addNewTenant(Tenant tenant);

    List<PropertyUnit> getAllPropertyUnits();

    List<Tenant> getAllTenants();

    PManager getPManagerById(int id);

    Tenant addNewTenant(Tenant tenant, int pManagerId, int roomNameOrNumber);

    PropertyUnit getPropertyByRoomNumOrName(String roomNumOrName);

    Role getRoleById(int id);

    Role addNewRole(Role role);

    String deletePropertyManager(int id);

    //delete role
    String deleteRole(int id);

    // find pmanager by email and password
    PManager findPManagerByPasswordAndEmail(String email, String password);
    PManager findPManagerByPhoneAndPassword(String phoneNumber, String password);

    // fetch rent payment overview
    List<RentPayment> getRentPaymentOverview(String month, String year);
    List<DetailedRentPaymentInfoDTO> getDetailedRentPayments(String month, String year, String roomName, String rooms, String tenantName, Integer tenantId, Boolean rentPaymentStatus, Boolean paidLate, Boolean tenantActive);
}

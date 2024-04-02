package com.propertymanagement.PropertyManagement.dao;

import com.propertymanagement.PropertyManagement.dto.PManagerLoginDTO;
import com.propertymanagement.PropertyManagement.entity.PManager;
import com.propertymanagement.PropertyManagement.entity.PropertyUnit;
import com.propertymanagement.PropertyManagement.entity.Role;
import com.propertymanagement.PropertyManagement.entity.Tenant;

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
}

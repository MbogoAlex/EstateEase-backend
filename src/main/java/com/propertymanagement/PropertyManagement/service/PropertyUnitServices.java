package com.propertymanagement.PropertyManagement.service;

import com.propertymanagement.PropertyManagement.dao.PManagerDao;
import com.propertymanagement.PropertyManagement.dto.PropertyUnitDTO;
import com.propertymanagement.PropertyManagement.entity.PManager;
import com.propertymanagement.PropertyManagement.entity.PropertyUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PropertyUnitServices {
    private PManagerDao appDao;
    @Autowired
    public PropertyUnitServices(PManagerDao appDao) {
        this.appDao = appDao;
    }

    // Add new property
    @Transactional
    public PropertyUnit addNewProperty(PropertyUnitDTO propertyUnitDTO, int propertyManagerId) {
        int roleId = 0;

        PropertyUnit propertyUnit = new PropertyUnit();
        propertyUnit.setNumberOfRooms(propertyUnitDTO.getNumberOfRooms());
        propertyUnit.setPropertyDescription(propertyUnitDTO.getPropertyDescription());
        propertyUnit.setPropertyNumberOrName(propertyUnit.getPropertyNumberOrName());
        propertyUnit.setMonthlyRent(propertyUnitDTO.getMonthlyRent());
        propertyUnit.setPropertyAddedAt(LocalDateTime.now());
        propertyUnit.setPropertyAssignmentStatus(false);

        // set the property manager that has added the property

        PManager pManager = appDao.getPManagerById(propertyManagerId);
        propertyUnit.setPManager(pManager);

        // add null tenants
//        propertyUnit.setTenants(null);

        return appDao.addNewUnit(propertyUnit);
    }




}

package com.propertymanagement.PropertyManagement.service;

import com.propertymanagement.PropertyManagement.dao.PManagerDao;
import com.propertymanagement.PropertyManagement.dao.PropertyUnitDao;
import com.propertymanagement.PropertyManagement.dao.TenantDao;
import com.propertymanagement.PropertyManagement.dto.PropertyUnitDTO;
import com.propertymanagement.PropertyManagement.dto.RentPaymentOverviewDTO;
import com.propertymanagement.PropertyManagement.dto.propertyResponse.PropertyTenantDTO;
import com.propertymanagement.PropertyManagement.dto.propertyResponse.PropertyUnitResponseDTO;
import com.propertymanagement.PropertyManagement.entity.PManager;
import com.propertymanagement.PropertyManagement.entity.PropertyUnit;
import com.propertymanagement.PropertyManagement.entity.RentPayment;
import com.propertymanagement.PropertyManagement.entity.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class PropertyUnitServiceImpl implements PropertyUnitService{

    private PropertyUnitDao propertyUnitDao;
    private PManagerDao pmAppDao;
    private TenantDao tenantDao;
    @Autowired
    public PropertyUnitServiceImpl(
            PropertyUnitDao propertyUnitDao,
            PManagerDao pmAppDao,
            TenantDao tenantDao
    ) {
        this.propertyUnitDao = propertyUnitDao;
        this.pmAppDao = pmAppDao;
        this.tenantDao = tenantDao;
    }
    @Transactional
    @Override
    public PropertyUnitResponseDTO addNewProperty(PropertyUnitDTO propertyUnitDTO) {
        System.out.println("ADDING PROPERTY, PMANAGER ID:" +propertyUnitDTO.getPropertyManagerId());
        PropertyUnit propertyUnit = new PropertyUnit();
        // set no. of rooms
        propertyUnit.setNumberOfRooms(propertyUnitDTO.getNumberOfRooms());

        // set property no. or name
        propertyUnit.setPropertyNumberOrName(propertyUnitDTO.getPropertyNumberOrName());

        // set property description
        propertyUnit.setPropertyDescription(propertyUnitDTO.getPropertyDescription());

        // set monthly rent
        propertyUnit.setMonthlyRent(propertyUnitDTO.getMonthlyRent());

        // set time added
        propertyUnit.setPropertyAddedAt(LocalDateTime.now());

        // set assignment status
        propertyUnit.setPropertyAssignmentStatus(false);

//         set tenants
//        propertyUnit.setTenants();

        // set pmanager
        PManager pManager = pmAppDao.getPManagerById(propertyUnitDTO.getPropertyManagerId());
        propertyUnit.setPManager(pManager);


        return mapPropertyToPropertyDto(pmAppDao.addNewUnit(propertyUnit));
    }
    @Transactional
    @Override
    public PropertyUnitResponseDTO updateProperty(PropertyUnitDTO propertyUnitDTO, int propertyId) {
        PropertyUnit propertyUnit = propertyUnitDao.getPropertyByPropertyId(propertyId);
        propertyUnit.setPropertyNumberOrName(propertyUnitDTO.getPropertyNumberOrName());
        propertyUnit.setPropertyDescription(propertyUnitDTO.getPropertyDescription());
        return mapPropertyToPropertyDto(propertyUnitDao.updateProperty(propertyUnit));
    }
    @Transactional
    @Override
    public PropertyUnitResponseDTO archiveProperty(int propertyId, int tenantId) {
        PropertyUnit propertyUnit = propertyUnitDao.getPropertyByPropertyId(propertyId);
        Tenant tenant = tenantDao.getTenantByTenantId(tenantId);

        // archive property
        propertyUnit.setPropertyAssignmentStatus(false);

        // archive tenant
        tenant.setTenantActive(false);

        // save updated tenant
        tenantDao.updateTenant(tenant);

        return mapPropertyToPropertyDto(propertyUnitDao.updateProperty(propertyUnit));
    }
    @Transactional
    @Override
    public String deleteProperty(int id) {
        return propertyUnitDao.deleteProperty(id);
    }

    @Override
    public List<PropertyUnitResponseDTO> getAllProperties() {
        List<PropertyUnitResponseDTO> propertiesDto = new ArrayList<>();
        List<PropertyUnit> properties = propertyUnitDao.getAllProperties();
        for(PropertyUnit propertyUnit : properties) {
            propertiesDto.add(mapPropertyToPropertyDto(propertyUnit));
        }
        return propertiesDto;
    }

    @Override
    public PropertyUnitResponseDTO getPropertyByPropertyId(int propertyId) {
        PropertyUnit propertyUnit = propertyUnitDao.getPropertyByPropertyId(propertyId);

        return mapPropertyToPropertyDto(propertyUnit);
    }

    @Override
    public List<PropertyUnitResponseDTO> fetchAllOccupiedUnits() {
        List<PropertyUnitResponseDTO> propertiesDto = new ArrayList<>();
        List<PropertyUnit> properties = propertyUnitDao.fetchAllOccupiedUnits();
        for(PropertyUnit propertyUnit : properties) {
            for(Tenant tenant : propertyUnit.getTenants()) {
                if(tenant.getTenantActive()) {
                    propertiesDto.add(mapPropertyToPropertyDto(propertyUnit));
                }
            }

        }
        return propertiesDto;
    }



    PropertyUnitResponseDTO mapPropertyToPropertyDto(PropertyUnit propertyUnit) {
        PropertyUnitResponseDTO propertyUnitResponseDTO = new PropertyUnitResponseDTO();
        propertyUnitResponseDTO.setPropertyUnitId(propertyUnit.getPropertyUnitId());
        propertyUnitResponseDTO.setNumberOfRooms(propertyUnit.getNumberOfRooms());
        propertyUnitResponseDTO.setPropertyNumberOrName(propertyUnit.getPropertyNumberOrName());
        propertyUnitResponseDTO.setPropertyDescription(propertyUnit.getPropertyDescription());
        propertyUnitResponseDTO.setMonthlyRent(propertyUnit.getMonthlyRent());
        propertyUnitResponseDTO.setPropertyAddedAt(propertyUnit.getPropertyAddedAt().toString());
        propertyUnitResponseDTO.setPropertyAssignmentStatus(propertyUnit.getPropertyAssignmentStatus());
        for(Tenant tenant : propertyUnit.getTenants()) {
            PropertyTenantDTO propertyTenant = new PropertyTenantDTO();
            propertyTenant.setTenantId(tenant.getTenantId());
            propertyTenant.setFullName(tenant.getFullName());
            propertyTenant.setEmail(tenant.getEmail());
            propertyTenant.setTenantAddedAt(tenant.getTenantAddedAt().toString());
            propertyTenant.setPhoneNumber(tenant.getPhoneNumber());
            propertyTenant.setTenantActive(tenant.getTenantActive());
            propertyUnitResponseDTO.getTenants().add(propertyTenant);

        }
        return propertyUnitResponseDTO;
    }


}

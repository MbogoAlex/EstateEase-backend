package com.propertymanagement.PropertyManagement.service;

import com.propertymanagement.PropertyManagement.dao.PManagerDao;
import com.propertymanagement.PropertyManagement.dto.PManagerDTO;
import com.propertymanagement.PropertyManagement.dto.PManagerLoginDTO;
import com.propertymanagement.PropertyManagement.dto.RentPaymentOverviewDTO;
import com.propertymanagement.PropertyManagement.dto.RoleDTO;
import com.propertymanagement.PropertyManagement.dto.pManagerResponse.PManagerResponseDTO;
import com.propertymanagement.PropertyManagement.dto.propertyResponse.PropertyTenantDTO;
import com.propertymanagement.PropertyManagement.dto.tenantResponse.TenantPropertyDTO;
import com.propertymanagement.PropertyManagement.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PManagerServiceImpl implements PManagerService {
    private PManagerDao pManagerDao;
    @Autowired
    public PManagerServiceImpl(PManagerDao pManagerDao) {
        this.pManagerDao = pManagerDao;
    }

    // Add new role
    @Transactional
    public Role addNewRole(RoleDTO roleDTO) {
        Role role = new Role();
        role.setRoleName(roleDTO.getRoleName());
        return pManagerDao.addNewRole(role);
    }

    @Transactional
    public String removePropertyManager(int id) {
        return pManagerDao.deletePropertyManager(id);
    }
    @Transactional
    public String removeRole(int id) {
        return pManagerDao.deleteRole(id);
    }
    @Transactional
    @Override
    public PManagerResponseDTO addNewPManager(PManagerDTO pManagerDTO) {
        int roleId = pManagerDTO.getRoleId();
        Role role = pManagerDao.getRoleById(roleId);
        PManager pManager = new PManager();
        pManager.setFullName(pManagerDTO.getFullName());
        pManager.setEmail(pManagerDTO.getEmail());
        pManager.setPhoneNumber(pManagerDTO.getPhoneNumber());
        pManager.setPassword(pManagerDTO.getPassword());
        pManager.setNationalIdOrPassportNumber(pManagerDTO.getNationalIdOrPassportNumber());
        pManager.setPropertyManagerAddedAt(LocalDateTime.now());
        pManager.setpManagerActiveStatus(true);
        pManager.getRoles().add(role);

        // set property units to null
//        pManager.setPropertyUnits(null);

        // set tenants to null
//        pManager.setTenants(null);

        return mapPManagerToPManagerResponseDTO(pManagerDao.addNewPManager(pManager));
    }
    @Transactional
    @Override
    public PManagerResponseDTO updatePManager(PManagerDTO pManagerDTO, int pManagerId) {
        System.out.println("GETTING ID: "+pManagerId);
        PManager pManager = pManagerDao.getPManagerById(pManagerId);
        pManager.setFullName(pManagerDTO.getFullName());
        pManager.setNationalIdOrPassportNumber(pManagerDTO.getNationalIdOrPassportNumber());
        pManager.setPhoneNumber(pManagerDTO.getPhoneNumber());
        pManager.setEmail(pManagerDTO.getEmail());
        pManager.setPassword(pManagerDTO.getPassword());
        return mapPManagerToPManagerResponseDTO(pManagerDao.updatePmanager(pManager));
    }
    @Transactional
    @Override
    public PManagerResponseDTO archivePManager(int pManagerId) {
        PManager pManager = pManagerDao.getPManagerById(pManagerId);
        pManager.setpManagerActiveStatus(false);
        return mapPManagerToPManagerResponseDTO(pManagerDao.archivePManager(pManager));
    }

    @Override
    public PManagerResponseDTO pManagerLogin(PManagerLoginDTO pManagerLoginDTO) {
        String email = pManagerLoginDTO.getEmail();
        String password = pManagerLoginDTO.getPassword();
        PManager pManager = pManagerDao.findPManagerByPasswordAndEmail(email, password);

        return mapPManagerToPManagerResponseDTO(pManager);
    }

    @Override
    public RentPaymentOverviewDTO getRentPaymentOverview(String month, String year) {
        Double totalExpectedRent = 0.0;
        Double paidAmount = 0.0;
        int clearedUnits = 0;

        List<RentPayment> rentPayments = pManagerDao.getRentPaymentOverview(month, year);
        int totalUnits = rentPayments.size();
        for(RentPayment rentPayment : rentPayments) {
            totalExpectedRent = totalExpectedRent + rentPayment.getMonthlyRent();

            if(rentPayment.getPaymentStatus()) {
                paidAmount = paidAmount + rentPayment.getPaidAmount();
                clearedUnits = clearedUnits + 1;
            }
        }
        Double deficit = totalExpectedRent - paidAmount;
        int unclearedUnits = totalUnits - clearedUnits;

        RentPaymentOverviewDTO rentPaymentOverviewDTO = new RentPaymentOverviewDTO();
        rentPaymentOverviewDTO.setTotalExpectedRent(totalExpectedRent);
        rentPaymentOverviewDTO.setTotalUnits(totalUnits);
        rentPaymentOverviewDTO.setPaidAmount(paidAmount);
        rentPaymentOverviewDTO.setClearedUnits(clearedUnits);
        rentPaymentOverviewDTO.setDeficit(deficit);
        rentPaymentOverviewDTO.setUnclearedUnits(unclearedUnits);

        return rentPaymentOverviewDTO;
    }

    PManagerResponseDTO mapPManagerToPManagerResponseDTO(PManager pManager) {
        PManagerResponseDTO pManagerResponseDTO = new PManagerResponseDTO();
        pManagerResponseDTO.setPManagerId(pManager.getpManagerId());
        pManagerResponseDTO.setNationalIdOrPassportNumber(pManager.getNationalIdOrPassportNumber());
        pManagerResponseDTO.setFullName(pManager.getFullName());
        pManagerResponseDTO.setPhoneNumber(pManager.getPhoneNumber());
        pManagerResponseDTO.setEmail(pManager.getEmail());
        pManagerResponseDTO.setPropertyManagerAddedAt(pManager.getPropertyManagerAddedAt().toString());

        for(PropertyUnit propertyUnit : pManager.getPropertyUnits()) {
            TenantPropertyDTO tenantPropertyDTO = new TenantPropertyDTO();
            tenantPropertyDTO.setPropertyUnitId(propertyUnit.getPropertyUnitId());
            tenantPropertyDTO.setPropertyNumberOrName(propertyUnit.getPropertyNumberOrName());
            tenantPropertyDTO.setPropertyDescription(propertyUnit.getPropertyDescription());
            tenantPropertyDTO.setMonthlyRent(propertyUnit.getMonthlyRent());
            pManagerResponseDTO.getPropertyUnits().add(tenantPropertyDTO);
        }

        for(Tenant tenant : pManager.getTenants()) {
            PropertyTenantDTO propertyTenantDTO = new PropertyTenantDTO();
            propertyTenantDTO.setTenantId(tenant.getTenantId());
            propertyTenantDTO.setFullName(tenant.getFullName());
            propertyTenantDTO.setPhoneNumber(tenant.getPhoneNumber());
            propertyTenantDTO.setEmail(tenant.getEmail());
            propertyTenantDTO.setTenantAddedAt(tenant.getTenantAddedAt().toString());
            propertyTenantDTO.setTenantActive(tenant.getTenantActive());
            pManagerResponseDTO.getTenants().add(propertyTenantDTO);
        }
        return pManagerResponseDTO;
    }


}

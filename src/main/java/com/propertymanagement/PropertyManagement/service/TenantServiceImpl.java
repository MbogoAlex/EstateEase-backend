package com.propertymanagement.PropertyManagement.service;

import com.propertymanagement.PropertyManagement.dao.PManagerDao;
import com.propertymanagement.PropertyManagement.dao.PropertyUnitDao;
import com.propertymanagement.PropertyManagement.dao.TenantDao;
import com.propertymanagement.PropertyManagement.dto.*;
import com.propertymanagement.PropertyManagement.dto.tenantResponse.TenantPropertyDTO;
import com.propertymanagement.PropertyManagement.dto.tenantResponse.TenantResponseDTO;
import com.propertymanagement.PropertyManagement.entity.*;
import com.propertymanagement.PropertyManagement.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@Service
public class TenantServiceImpl implements TenantService{

    private PManagerDao pmAppDao;
    private TenantDao tenantDao;
    private PropertyUnitDao propertyUnitDao;
    @Autowired
    public TenantServiceImpl(
            PManagerDao pmAppDao,
            TenantDao tenantDao,
            PropertyUnitDao propertyUnitDao
    ) {
        this.pmAppDao = pmAppDao;
        this.tenantDao = tenantDao;
        this.propertyUnitDao = propertyUnitDao;
    }
    @Transactional
    @Override
    public TenantResponseDTO addNewTenant(TenantDTO tenantDTO) {
        System.out.println("ADDING NEW TENANT: "+tenantDTO.toString());
        Tenant tenant = new Tenant();
        // set tenant name
        tenant.setFullName(tenantDTO.getFullName());


        // set tenant phone number
        tenant.setPhoneNumber(tenantDTO.getPhoneNumber());

        // set tenant nt.id

        tenant.setNationalIdOrPassportNumber(tenantDTO.getNationalIdOrPassportNumber());

        // set tenant email

        tenant.setEmail(tenantDTO.getEmail());

        // set tenant password
        tenant.setPassword(tenantDTO.getPassword());

        // set tenant added at
        tenant.setTenantAddedAt(LocalDateTime.now());

        // set tenant active status
        tenant.setTenantActive(true);

        // set property unit
        PropertyUnit propertyUnit = propertyUnitDao.getPropertyByPropertyId(tenantDTO.getPropertyUnitId());
        tenant.setPropertyUnit(propertyUnit);

        // set rent payment status
        RentPayment rentPayment = new RentPayment();
        rentPayment.setTenant(tenant);
        rentPayment.setTransactionId(null);
        rentPayment.setMonth(LocalDateTime.now().getMonth());
        rentPayment.setDueDate(LocalDate.now());
        rentPayment.setPenaltyPerDay(tenantDTO.getPenaltyPerDay());
        rentPayment.setPenaltyActive(true);
        rentPayment.setMonthlyRent(propertyUnit.getMonthlyRent());
        rentPayment.setPaymentStatus(false);



//        rentPayment.setDaysLate(0);


        rentPayment.setPaidAt(null);
        tenant.getRentPayments().add(rentPayment);

        // set role
        Role role = pmAppDao.getRoleById(tenantDTO.getRoleId());
        tenant.getRoles().add(role);





        rentPayment.setUnitId(propertyUnit.getPropertyUnitId());


        propertyUnit.setPropertyAssignmentStatus(true);
        propertyUnitDao.updateProperty(propertyUnit);

        // set tenant added by id
        PManager pManager = pmAppDao.getPManagerById(tenantDTO.getTenantAddedByPManagerId());
        tenant.setpManager(pManager);


        return mapTenantToTenantResponseDTO(tenantDao.addNewTenant(tenant));
    }
    @Transactional
    @Override
    public TenantResponseDTO updateTenant(TenantUpdateDTO tenantDTO) {

        Tenant tenant = tenantDao.getTenantByTenantId(tenantDTO.getTenantId());
        // set tenant name
        tenant.setFullName(tenantDTO.getFullName());


        // set tenant phone number
        tenant.setPhoneNumber(tenantDTO.getPhoneNumber());

        // set tenant nt.id

        tenant.setNationalIdOrPassportNumber(tenantDTO.getNationalIdOrPassportNumber());

        // set tenant email

        tenant.setEmail(tenantDTO.getEmail());

        // set tenant password
        tenant.setPassword(tenantDTO.getPassword());

        // set tenant added at
        tenant.setTenantAddedAt(LocalDateTime.now());

        // update monthly rent
        for(RentPayment payment : tenant.getRentPayments()) {
            payment.setMonthlyRent(tenant.getPropertyUnit().getMonthlyRent());
            payment.setUnitId(tenantDTO.getPropertyUnitId());
            payment.setPenaltyPerDay(tenantDTO.getPenaltyPerDay());
        }

        tenantDao.updateTenant(tenant);
        return mapTenantToTenantResponseDTO(tenant);
    }

    @Override
    public List<TenantResponseDTO> getAllTenants() {
        List<Tenant> tenants = tenantDao.getAllTenants();
        List<TenantResponseDTO> tenantsDTO = new ArrayList<>();

        for(Tenant tenant : tenants) {
            tenantsDTO.add(mapTenantToTenantResponseDTO(tenant));
        }
        return tenantsDTO;
    }

    @Override
    public TenantResponseDTO getTenantByTenantId(int tenantId) {
        Tenant tenant = tenantDao.getTenantByTenantId(tenantId);
        return mapTenantToTenantResponseDTO(tenant);
    }

    @Transactional
    @Override
    public RentPayment addNewRentPaymentRow(RentPaymentDTO rentPaymentDTO) {

        List<Tenant> tenants = tenantDao.getActiveTenants();

        System.out.println("RENT DUE ON: "+rentPaymentDTO.getDueDate());

        for(Tenant tenant : tenants) {
            RentPayment rentPayment = new RentPayment();
            // set tenant
            rentPayment.setTenant(tenant);
            rentPayment.setUnitId(tenant.getPropertyUnit().getPropertyUnitId());

            // set transactionId
//        rentPayment.setTransactionId("####");

            // set payable rent
            rentPayment.setMonthlyRent(tenant.getPropertyUnit().getMonthlyRent());

            // set penalty
            rentPayment.setPenaltyActive(rentPaymentDTO.getPenaltyActive());
            rentPayment.setPenaltyPerDay(rentPaymentDTO.getPenaltyPerDay());


            // set rent payment status
            rentPayment.setPaymentStatus(false);

            // set current month
            rentPayment.setMonth(LocalDateTime.now().getMonth());

            // set due date
            rentPayment.setDueDate(rentPaymentDTO.getDueDate());

            // save rent payment status
            tenantDao.addNewRentPaymentRow(rentPayment);
        }



        return null;
    }
    @Transactional
    @Override
    public RentPayment payRent(RentPaymentRequestDTO rentPaymentRequestDTO, int rentPaymentTblId) {
        Random random = new Random();
        Tenant tenant = tenantDao.getTenantByTenantId(rentPaymentRequestDTO.getTenantId());
        RentPayment rentPayment = tenantDao.getRentPaymentRow(rentPaymentTblId);
        rentPayment.setTransactionId(String.valueOf(random.nextInt()));
        rentPayment.setPaidAmount(tenant.getPropertyUnit().getMonthlyRent());
        rentPayment.setPaymentStatus(true);
        rentPayment.setPaidAt(LocalDateTime.now());


        return tenantDao.payRent(rentPayment);
    }
    @Transactional
    @Override
    public TenantResponseDTO archiveTenant(int tenantId, int propertyId) {
        // get tenant
        Tenant tenant = tenantDao.getTenantByTenantId(tenantId);
        // set new tenant status
        tenant.setTenantActive(false);

        // get property
        PropertyUnit propertyUnit = propertyUnitDao.getPropertyByPropertyId(propertyId);
        // set new property status
        propertyUnit.setPropertyAssignmentStatus(false);

        // save updated property
        propertyUnitDao.archiveProperty(propertyUnit);

        return mapTenantToTenantResponseDTO(tenantDao.archiveTenant(tenant));
    }

    @Override
    public TenantResponseDTO tenantLogin(TenantLoginDTO tenantLoginDTO) {
        String roomNumberOrName = tenantLoginDTO.getTenantRoomNameOrNumber();
        String tenantPassword = tenantLoginDTO.getTenantPassword();
        String phoneNumber = tenantLoginDTO.getTenantPhoneNumber();

        // fetch tenant by password and phone number
        Tenant tenant = tenantDao.fetchTenantByPasswordAndPhoneNumber(tenantPassword, phoneNumber);
        System.out.println("roomNumberOrName: "+roomNumberOrName);
        System.out.println("tenant.getPropertyUnit().getPropertyNumberOrName(): "+tenant.getPropertyUnit().getPropertyNumberOrName());
        if(tenant.getPropertyUnit().getPropertyNumberOrName().equals(roomNumberOrName)) {
            return mapTenantToTenantResponseDTO(tenant);
        } else {
            throw new DataNotFoundException("Invalid credentials");
        }

    }

    TenantResponseDTO mapTenantToTenantResponseDTO(Tenant tenant) {
        TenantResponseDTO tenantResponseDTO = new TenantResponseDTO();
        tenantResponseDTO.setTenantId(tenant.getTenantId());
        tenantResponseDTO.setFullName(tenant.getFullName());
        tenantResponseDTO.setNationalIdOrPassportNumber(tenant.getNationalIdOrPassportNumber());
        tenantResponseDTO.setPhoneNumber(tenant.getPhoneNumber());
        tenantResponseDTO.setEmail(tenant.getEmail());
        tenantResponseDTO.setTenantAddedAt(tenant.getTenantAddedAt().toString());
        tenantResponseDTO.setTenantActive(tenant.getTenantActive());
        tenantResponseDTO.getRentPayments().addAll(tenant.getRentPayments());

        // tenant property unit details

        TenantPropertyDTO tenantPropertyDTO = new TenantPropertyDTO();
        tenantPropertyDTO.setPropertyUnitId(tenant.getPropertyUnit().getPropertyUnitId());
        tenantPropertyDTO.setNumberOfRooms(tenant.getPropertyUnit().getNumberOfRooms());
        tenantPropertyDTO.setPropertyNumberOrName(tenant.getPropertyUnit().getPropertyNumberOrName());
        tenantPropertyDTO.setPropertyDescription(tenant.getPropertyUnit().getPropertyDescription());
        tenantPropertyDTO.setMonthlyRent(tenant.getPropertyUnit().getMonthlyRent());

        // assign unit to tenant
        tenantResponseDTO.setPropertyUnit(tenantPropertyDTO);

        return tenantResponseDTO;
    }
}

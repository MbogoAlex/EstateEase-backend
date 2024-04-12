package com.propertymanagement.PropertyManagement.service;

import com.propertymanagement.PropertyManagement.dao.PManagerDao;
import com.propertymanagement.PropertyManagement.dto.*;
import com.propertymanagement.PropertyManagement.dto.pManagerResponse.PManagerResponseDTO;
import com.propertymanagement.PropertyManagement.dto.propertyResponse.PropertyTenantDTO;
import com.propertymanagement.PropertyManagement.dto.tenantResponse.TenantPropertyDTO;
import com.propertymanagement.PropertyManagement.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Override
    public List<RentPaymentDetailsDTO> getRentPaymentDetailedInfo(String month, String year, Integer rooms, String roomName, String tenantName) {
        System.out.println("MONTH: "+month);
        System.out.println("YEAR: "+year);
        System.out.println("ROOMS: "+rooms);
        System.out.println("ROOMNAME: "+roomName);
        System.out.println("TENANTNAME: "+tenantName);
        List<RentPaymentDetailsDTO> rentPaymentsDTO = new ArrayList<>();
        List<RentPayment> rentPayments = pManagerDao.getRentPaymentOverview(month, year);

        List<RentPayment> processedData = new ArrayList<>();

        if(rooms != null) {
            List<RentPayment> listToFilter;
            System.out.println("TRUE: "+tenantName == null || roomName == null);
            if(!tenantName.isEmpty() || !roomName.isEmpty()) {
                listToFilter = processedData;
            } else {
                listToFilter = rentPayments;
            }
            for(RentPayment rentPayment : listToFilter) {
                if(rentPayment.getTenant().getPropertyUnit().getNumberOfRooms() == rooms) {
                    System.out.println("ADDED ROOM");
                    processedData.add(rentPayment);

                }
            }
            System.out.println("DONE WITH THE FUNCTION");
        }
        if(!roomName.isEmpty()) {
            System.out.println("STARTING THIS FUNCTION");
            List<RentPayment> listToFilter;
            if(rooms != null || !tenantName.isEmpty()) {
                listToFilter = processedData;
                System.out.println("ASSIGNED PROCESSED DATA "+" SIZE: "+listToFilter.size());
            } else {
                listToFilter = rentPayments;
                System.out.println("ASSIGNED RENT PAYMENTS "+" SIZE: "+listToFilter.size());
            }

            for(RentPayment rentPayment : listToFilter) {
                System.out.println("COMPARING: "+rentPayment.getTenant().getPropertyUnit().getPropertyNumberOrName().toLowerCase()+" WITH "+roomName.toLowerCase());
                if(rentPayment.getTenant().getPropertyUnit().getPropertyNumberOrName().toLowerCase().contains(roomName.toLowerCase())){
                    processedData.add(rentPayment);
                }
            }
        }

//        for(RentPayment rentPayment : rentPayments) {
//
//            if(rooms != null) {
//                System.out.println("ROOMS NOT NULL");
//                System.out.println("COMPARING: "+rentPayment.getTenant().getPropertyUnit().getNumberOfRooms()+ " WITH "+rooms);
//                if(rentPayment.getTenant().getPropertyUnit().getNumberOfRooms() == rooms) {
//                    System.out.println("ADDED ROOM");
//                    processedData.add(rentPayment);
//                }
//            }
//            if(roomName != null) {
//                if(rentPayment.getTenant().getPropertyUnit().getPropertyNumberOrName().toLowerCase().contains(roomName.toLowerCase())) {
//                    processedData.add(rentPayment);
//                }
//            }
//            if(tenantName != null) {
//                if(rentPayment.getTenant().getFullName().toLowerCase().contains(tenantName.toLowerCase())) {
//                    processedData.add(rentPayment);
//                }
//            }
//
//        }

        System.out.println("ARE PARAMETERS EMPTY: "+tenantName != null || rooms != null || roomName != null);

        if(processedData.isEmpty() && (tenantName != null || rooms != null || roomName != null)) {
            for(RentPayment data : processedData) {
                rentPaymentsDTO.add(mapRentPaymentsToRentPaymentsDetailsDTO(data));
            }
        } else if(!processedData.isEmpty()) {
            System.out.println();
            System.out.println("LIST NOT EMPTY ");
            Set<RentPayment> distinctData = new HashSet<>(processedData);
            List<RentPayment> distinctList = new ArrayList<>(distinctData);
            System.out.println("NO. OF ITEMS: "+distinctList.size());

            for(RentPayment rent : distinctList) {
                rentPaymentsDTO.add(mapRentPaymentsToRentPaymentsDetailsDTO(rent));
            }
        } else {
            System.out.println("ADDING ALL");
            processedData.addAll(rentPayments);
            for(RentPayment payment : processedData) {
                rentPaymentsDTO.add(mapRentPaymentsToRentPaymentsDetailsDTO(payment));
            }
        }
        return rentPaymentsDTO;
    }

    @Override
    public List<DetailedRentPaymentInfoDTO> getDetailedRentPayments(String month, String year, String roomName, Integer rooms, String tenantName, Boolean rentPaymentStatus) {
        return pManagerDao.getDetailedRentPayments(month, year, roomName, rooms, tenantName, rentPaymentStatus);
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


    RentPaymentDetailsDTO mapRentPaymentsToRentPaymentsDetailsDTO(RentPayment rentPayment) {
        RentPaymentDetailsDTO rentPaymentDetailsDTO = new RentPaymentDetailsDTO();
        RentPaymentDetailsDTO.TenantDataDTO tenantDataDTO = new RentPaymentDetailsDTO.TenantDataDTO();
        rentPaymentDetailsDTO.setRentPaymentTblId(rentPayment.getRentPaymentTblId());
        rentPaymentDetailsDTO.setTransactionId(rentPayment.getTransactionId());
        rentPaymentDetailsDTO.setMonth(rentPayment.getMonth());
        rentPaymentDetailsDTO.setYear(rentPayment.getYear());
        rentPaymentDetailsDTO.setDueDate(rentPayment.getDueDate());
        rentPaymentDetailsDTO.setRentPaymentStatus(rentPayment.getPaymentStatus());
        rentPaymentDetailsDTO.setPenaltyActive(rentPayment.getPenaltyActive());
        rentPaymentDetailsDTO.setPenaltyPerDay(rentPayment.getPenaltyPerDay());
        rentPaymentDetailsDTO.setPaidAmount(rentPayment.getPaidAmount());
        rentPaymentDetailsDTO.setPaidAt(rentPayment.getPaidAt());
        rentPaymentDetailsDTO.setPaidLate(rentPayment.getPaidLate());
        rentPaymentDetailsDTO.setUnitName(rentPayment.getTenant().getPropertyUnit().getPropertyNumberOrName());
        tenantDataDTO.setTenantId(rentPayment.getTenant().getTenantId());
        tenantDataDTO.setFullName(rentPayment.getTenant().getFullName());
        tenantDataDTO.setNationalIdOrPassportNumber(rentPayment.getTenant().getNationalIdOrPassportNumber());
        tenantDataDTO.setPhoneNumber(rentPayment.getTenant().getPhoneNumber());
        tenantDataDTO.setEmail(rentPayment.getTenant().getEmail());
        tenantDataDTO.setTenantAddedAt(rentPayment.getTenant().getTenantAddedAt());
        tenantDataDTO.setTenantActive(rentPayment.getTenant().getTenantActive());

        long daysLate;

        if(!rentPayment.getPaymentStatus() && ChronoUnit.DAYS.between(rentPayment.getDueDate(), LocalDateTime.now()) > 0) {
            daysLate = ChronoUnit.DAYS.between(rentPayment.getDueDate(), LocalDateTime.now());
        } else if (rentPayment.getPaymentStatus() && ChronoUnit.DAYS.between(rentPayment.getDueDate(), rentPayment.getPaidAt()) > 0) {
            daysLate = ChronoUnit.DAYS.between(rentPayment.getDueDate(), rentPayment.getPaidAt());
        } else {
            daysLate = 0;
        }

        rentPaymentDetailsDTO.setMonthlyRent(rentPayment.getMonthlyRent());
        rentPaymentDetailsDTO.setDaysLate(daysLate);

        rentPaymentDetailsDTO.setTenant(tenantDataDTO);

        return rentPaymentDetailsDTO;
    }


}

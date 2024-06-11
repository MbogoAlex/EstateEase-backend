package com.propertymanagement.PropertyManagement.service;

import com.propertymanagement.PropertyManagement.dao.PManagerDao;
import com.propertymanagement.PropertyManagement.dto.*;
import com.propertymanagement.PropertyManagement.dto.pManagerResponse.PManagerResponseDTO;
import com.propertymanagement.PropertyManagement.dto.propertyResponse.PropertyTenantDTO;
import com.propertymanagement.PropertyManagement.dto.tenantResponse.TenantPropertyDTO;
import com.propertymanagement.PropertyManagement.entity.*;
import com.propertymanagement.PropertyManagement.exception.DataNotFoundException;
import com.propertymanagement.PropertyManagement.reportModels.GeneralReport;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
        String phoneNumber = pManagerLoginDTO.getPhoneNumber();
        String password = pManagerLoginDTO.getPassword();
        PManager pManager = pManagerDao.findPManagerByPhoneAndPassword(phoneNumber, password);
        if(pManager.getpManagerActiveStatus()) {
            return mapPManagerToPManagerResponseDTO(pManager);
        } else {
            throw new DataNotFoundException("Invalid credentials");
        }


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
    public List<RentPaymentDetailsDTO> getRentPaymentDetailedInfo(String month, String year, String rooms, String roomName, String tenantName) {

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
                if(rentPayment.getTenant().getPropertyUnit().getRooms().equalsIgnoreCase(rooms)) {
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

//

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
    public List<DetailedRentPaymentInfoDTO> getDetailedRentPayments(String month, String year, String roomName, Integer rooms, String tenantName, Integer tenantId, Boolean rentPaymentStatus, Boolean paidLate, Boolean tenantActive) {

        List<DetailedRentPaymentInfoDTO> rentPayments = pManagerDao.getDetailedRentPayments(month, year, roomName, rooms, tenantName, tenantId, rentPaymentStatus, paidLate, tenantActive);
        for(DetailedRentPaymentInfoDTO rentPayment : rentPayments) {
            long daysLate;

            if(!rentPayment.getRentPaymentStatus() && ChronoUnit.DAYS.between(rentPayment.getDueDate(), LocalDateTime.now()) > 0) {
                daysLate = ChronoUnit.DAYS.between(rentPayment.getDueDate(), LocalDateTime.now());
            } else if (rentPayment.getRentPaymentStatus() && ChronoUnit.DAYS.between(rentPayment.getDueDate(), rentPayment.getPaidAt()) > 0) {
                daysLate = ChronoUnit.DAYS.between(rentPayment.getDueDate(), rentPayment.getPaidAt());
            } else {
                daysLate = 0;
            }
            rentPayment.setDaysLate(daysLate);
        }


        return rentPayments;
    }

    @Override
    public ByteArrayOutputStream generateGeneralRentPaymentsReport(String month, String year, String roomName, Integer rooms, String tenantName, Integer tenantId, Boolean rentPaymentStatus, Boolean paidLate, Boolean tenantActive) throws JRException {
        System.out.println("GENERATING REPORT");
        DecimalFormat formatter= new DecimalFormat("Ksh #,##0.00");
        Double totalExpectedRent = 0.0;
        Double paidAmount = 0.0;
        int clearedUnits = 0;

//        List<RentPayment> rentPayments = pManagerDao.getRentPaymentOverview(month, year);
        List<DetailedRentPaymentInfoDTO> detailedRentPaymentInfoDTOS = pManagerDao.getDetailedRentPayments(month, year, roomName, rooms, tenantName, tenantId, rentPaymentStatus, paidLate, tenantActive);

        int totalUnits = detailedRentPaymentInfoDTOS.size();

        Double deficit = totalExpectedRent - paidAmount;
        int unclearedUnits = totalUnits - clearedUnits;


        List<GeneralReport> generalReport = new ArrayList<>();
        for(DetailedRentPaymentInfoDTO rentPaymentDetailsDTO : detailedRentPaymentInfoDTOS) {
            totalExpectedRent = totalExpectedRent + rentPaymentDetailsDTO.getMonthlyRent();

            if(rentPaymentDetailsDTO.getRentPaymentStatus()) {
                paidAmount = paidAmount + rentPaymentDetailsDTO.getPaidAmount();
                clearedUnits = clearedUnits + 1;
            }
            System.out.println("CLEARED STAGE ONE");

            long daysLate;

            if(!rentPaymentDetailsDTO.getRentPaymentStatus() && ChronoUnit.DAYS.between(rentPaymentDetailsDTO.getDueDate(), LocalDateTime.now()) > 0) {
                daysLate = ChronoUnit.DAYS.between(rentPaymentDetailsDTO.getDueDate(), LocalDateTime.now());
            } else if (rentPaymentDetailsDTO.getRentPaymentStatus() && ChronoUnit.DAYS.between(rentPaymentDetailsDTO.getDueDate(), rentPaymentDetailsDTO.getPaidAt()) > 0) {
                daysLate = ChronoUnit.DAYS.between(rentPaymentDetailsDTO.getDueDate(), rentPaymentDetailsDTO.getPaidAt());
            } else {
                daysLate = 0;
            }
            rentPaymentDetailsDTO.setDaysLate(daysLate);

            GeneralReport report = new GeneralReport();

            String formattedRent = "";
            String formattedPenalty = "";
            String formattedPayableAmount = "";
            String rentPaidAt = "";
            String formattedPaidAt = "";

            if(rentPaymentDetailsDTO.getRentPaymentStatus()) {
                formattedRent = formatter.format(rentPaymentDetailsDTO.getMonthlyRent());
                formattedPenalty = formatter.format(rentPaymentDetailsDTO.getPenaltyPerDay());
                formattedPayableAmount = formatter.format(rentPaymentDetailsDTO.getPaidAmount());
                rentPaidAt = rentPaymentDetailsDTO.getPaidAt().toString();
                String[] paidAtParts = rentPaidAt.split("T");
                formattedPaidAt = paidAtParts[0];
            } else {
                formattedRent = formatter.format(rentPaymentDetailsDTO.getMonthlyRent());
                formattedPenalty = formatter.format(rentPaymentDetailsDTO.getPenaltyPerDay());
                if(rentPaymentDetailsDTO.getPenaltyActive()) {
                    if(daysLate > 0) {
                        formattedPayableAmount = formatter.format(rentPaymentDetailsDTO.getMonthlyRent() + (daysLate * rentPaymentDetailsDTO.getPenaltyPerDay()));
                    } else {
                        formattedPayableAmount = formattedRent;
                    }
                } else {
                    formattedPayableAmount = formattedRent;
                }
                formattedPaidAt = "#";
            }




            System.out.println("CLEARED STAGE TWO");

            report.setTenantName(rentPaymentDetailsDTO.getFullName());
            report.setTenantPhoneNo(rentPaymentDetailsDTO.getPhoneNumber());
            report.setRoom(rentPaymentDetailsDTO.getPropertyNumberOrName());
            report.setRooms(rentPaymentDetailsDTO.getNumberOfRooms().toString());
            report.setRent(formattedRent);
            report.setDueDate(rentPaymentDetailsDTO.getDueDate().toString());
            if(rentPaymentDetailsDTO.getRentPaymentStatus()) {
                report.setPaymentStatus("PAID");
            } else {
                report.setPaymentStatus("UNPAID");
            }
            report.setPenalty(rentPaymentDetailsDTO.getDaysLate()+" days * "+formattedPenalty);
            System.out.println("CLEARED STAGE THREE");
            report.setPayableAmount(formattedPayableAmount);
            System.out.println("CLEARED STAGE FOUR");
            report.setPaidAt(formattedPaidAt);
            System.out.println();
            generalReport.add(report);
        }

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(generalReport);
        String filePath = "/home/mbogo/dev-spring-boot/pManger-project/PropertyManagement/src/main/resources/templates/GeneralReport.jrxml";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("month", month);
        parameters.put("year", year);
        parameters.put("totalUnits", totalUnits);
        parameters.put("totalExpectedRent", formatter.format(totalExpectedRent));
        parameters.put("paidAmount", formatter.format(paidAmount));
        parameters.put("deficit", formatter.format(deficit));
        parameters.put("clearedUnits", clearedUnits);
        parameters.put("unclearedUnits", unclearedUnits);
        parameters.put("rentPaymentDataset", dataSource);

        JasperReport report = JasperCompileManager.compileReport(filePath);
        JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        configuration.setCompressed(true);
        exporter.setConfiguration(configuration);
        exporter.setExporterInput(new SimpleExporterInput(print));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
        exporter.exportReport();


        return byteArrayOutputStream;
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
        rentPaymentDetailsDTO.setRooms(rentPayment.getTenant().getPropertyUnit().getRooms());
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

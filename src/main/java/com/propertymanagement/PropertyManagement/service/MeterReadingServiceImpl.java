package com.propertymanagement.PropertyManagement.service;

import com.propertymanagement.PropertyManagement.dao.*;
import com.propertymanagement.PropertyManagement.dto.MeterReadingDTO;
import com.propertymanagement.PropertyManagement.dto.WaterMeterDataDTO;
import com.propertymanagement.PropertyManagement.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;
import java.util.List;


@Service
@Slf4j
public class MeterReadingServiceImpl implements MeterReadingService {
    private final MeterReadingDao meterReadingDao;
    private final PropertyUnitDao propertyUnitDao;

    private final SettingsDao settingsDao;

    private final TenantDao tenantDao;
    private final AdditionalExpensesDao additionalExpensesDao;


    @Autowired
    public MeterReadingServiceImpl(
            MeterReadingDao meterReadingDao,
            PropertyUnitDao propertyUnitDao,
            SettingsDao settingsDao,
            TenantDao tenantDao,
            AdditionalExpensesDao additionalExpensesDao
    ) {
        this.meterReadingDao = meterReadingDao;
        this.propertyUnitDao = propertyUnitDao;
        this.settingsDao = settingsDao;
        this.tenantDao = tenantDao;
        this.additionalExpensesDao = additionalExpensesDao;
    }
    @Transactional
    @Override
    public List<WaterMeterDataDTO> initializeMeterReading() {
        List<Tenant> tenants = tenantDao.getActiveTenants();
        Settings domain = settingsDao.findBySettingsKey("domain");
        Settings imagePath = settingsDao.findBySettingsKey("imagePath");
        AdditionalExpenses additionalExpenses = additionalExpensesDao.getExpense(1);
        List<WaterMeterDataDTO> waterMeterDataDTOS = new ArrayList<>();
        for(Tenant tenant : tenants) {
            WaterMeterData waterMeterData = new WaterMeterData();
            WaterMeterImage waterMeterImage = new WaterMeterImage();
            waterMeterImage.setName(null);
            waterMeterData.setPropertyUnit(tenant.getPropertyUnit());
            waterMeterData.setTenant(tenant);
            waterMeterData.setPricePerUnit(additionalExpenses.getCost());
            waterMeterData.setMeterReadingTaken(false);
            waterMeterData.setWaterMeterImage(waterMeterImage);
            waterMeterData.setMonth(LocalDateTime.now().getMonth().toString().toUpperCase());
            waterMeterData.setYear(String.valueOf(LocalDateTime.now().getYear()));
//            waterMeterData.setMonth("MAY");
//            waterMeterData.setYear("2024");
            waterMeterImage.setWaterMeterData(waterMeterData);
            WaterMeterDataDTO waterMeterDataDTO;
            waterMeterDataDTO = waterMeterDataToWaterMeterDataDTO(meterReadingDao.addMeterWaterReading(waterMeterData), domain.getValue(), imagePath.getValue());
            waterMeterDataDTOS.add(waterMeterDataDTO);
        }

        return waterMeterDataDTOS;
    }

    @Transactional
    @Override
    public WaterMeterDataDTO addMeterReading(MeterReadingDTO meterReadingDTO, MultipartFile image) throws IOException {
        System.out.println("ADDING METER READING, MONTH: "+meterReadingDTO.getMonth()+" YEAR: "+meterReadingDTO.getYear());
        WaterMeterData waterMeterData = meterReadingDao.getWaterMeterDataById(meterReadingDTO.getMeterDtTableId());
//        AdditionalExpenses additionalExpenses = additionalExpensesDao.getExpense(1);
        Tenant tenant = waterMeterData.getTenant();
        PropertyUnit propertyUnit = waterMeterData.getPropertyUnit();
        Settings imagePath = settingsDao.findBySettingsKey("imagePath");
        String path = imagePath.getValue();
        Settings domainName = settingsDao.findBySettingsKey("domain");
        String domain = domainName.getValue();

        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename().replace(" ", "");
        String filePath = path + fileName;

        File uploadFile = new File(filePath);
        image.transferTo(uploadFile);



        WaterMeterImage waterMeterImage = waterMeterData.getWaterMeterImage();
        waterMeterImage.setName(fileName);


        waterMeterData.setPropertyUnit(propertyUnit);
        waterMeterData.setTenant(tenant);
        waterMeterData.setWaterUnits(meterReadingDTO.getWaterUnits());
        waterMeterData.setMeterReadingDate(LocalDateTime.now());
        waterMeterData.setMonth(meterReadingDTO.getMonth().toUpperCase());
        waterMeterData.setYear(meterReadingDTO.getYear());
        waterMeterData.setMeterReadingTaken(true);
//        waterMeterData.setPricePerUnit(additionalExpenses.getCost());
        waterMeterData.setWaterMeterImage(waterMeterImage);
        waterMeterImage.setWaterMeterData(waterMeterData);


        propertyUnit.getWaterMeterData().add(waterMeterData);
//        propertyUnitDao.updateProperty(propertyUnit);

        return waterMeterDataToWaterMeterDataDTO(meterReadingDao.updateMeterReading(waterMeterData), domain, filePath);
    }
    @Transactional
    @Override
    public WaterMeterDataDTO updateMeterReading(MeterReadingDTO meterReadingDTO, MultipartFile image, int oldImageId) throws IOException {

        WaterMeterImage oldImage = meterReadingDao.getImageById(oldImageId);
        WaterMeterData waterMeterData = meterReadingDao.getWaterMeterDataById(meterReadingDTO.getMeterDtTableId());
        PropertyUnit propertyUnit = waterMeterData.getPropertyUnit();

        Settings result = settingsDao.findBySettingsKey("imagePath");
        String url = result.getValue();

        String oldImagePath = url + oldImage.getName();

        Path oldPath = Paths.get(oldImagePath);

        try {
            Files.delete(oldPath);
//            meterReadingDao.deleteImage(oldImageId);
        } catch (IOException e) {
            System.err.println("Error deleting image: " + e.getMessage());
        }

        Tenant tenant = waterMeterData.getTenant();

        String path = result.getValue();
        Settings domainName = settingsDao.findBySettingsKey("domain");
        String domain = domainName.getValue();

        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename().replace(" ", "");
        String filePath = path + fileName;

        File uploadFile = new File(filePath);
        image.transferTo(uploadFile);


        WaterMeterImage waterMeterImage = waterMeterData.getWaterMeterImage();
        waterMeterImage.setName(fileName);

        waterMeterData.setPropertyUnit(propertyUnit);
        waterMeterData.setTenant(tenant);
        waterMeterData.setWaterUnits(meterReadingDTO.getWaterUnits());
        waterMeterData.setMeterReadingDate(LocalDateTime.now());
        waterMeterData.setMonth(meterReadingDTO.getMonth().toUpperCase());
        waterMeterData.setYear(meterReadingDTO.getYear().toUpperCase());
        waterMeterData.setMeterReadingTaken(true);
        waterMeterData.setWaterMeterImage(waterMeterImage);

        return waterMeterDataToWaterMeterDataDTO(meterReadingDao.updateMeterReading(waterMeterData), domain, filePath);
    }

    @Override
    public List<WaterMeterDataDTO> getMeterWaterReadings(String month, String year, Boolean meterReadingTaken, String tenantName, String propertyName, String role) {

        String currentMonth = month;
        String currentYear = year;

        String previousReadingMonth = "";
        String previousReadingYear = currentYear;

        if(role != null && role.equalsIgnoreCase("caretaker")) {
            previousReadingMonth = month;
            previousReadingYear = year;
        } else {
            if(currentMonth.equalsIgnoreCase("january")) {
                previousReadingMonth = "December";
                previousReadingYear = String.valueOf((Integer.parseInt(currentYear) - 1));
            } else if(currentMonth.equalsIgnoreCase("february")) {
                previousReadingMonth = "January";
            } else if(currentMonth.equalsIgnoreCase("march")) {
                previousReadingMonth = "February";
            } else if(currentMonth.equalsIgnoreCase("april")) {
                previousReadingMonth = "March";
            } else if(currentMonth.equalsIgnoreCase("may")) {
                previousReadingMonth = "April";
            } else if(currentMonth.equalsIgnoreCase("june")) {
                previousReadingMonth = "May";
            } else if(currentMonth.equalsIgnoreCase("july")) {
                previousReadingMonth = "June";
            } else if(currentMonth.equalsIgnoreCase("august")) {
                previousReadingMonth = "July";
            } else if(currentMonth.equalsIgnoreCase("september")) {
                previousReadingMonth = "August";
            } else if(currentMonth.equalsIgnoreCase("october")) {
                previousReadingMonth = "September";
            } else if(currentMonth.equalsIgnoreCase("november")) {
                previousReadingMonth = "October";
            } else if(currentMonth.equalsIgnoreCase("december")) {
                previousReadingMonth = "November";
            }
        }







        System.out.println("CALLED");
        List<WaterMeterData> waterMeterDataList = meterReadingDao.getMeterWaterReadings(previousReadingMonth, previousReadingYear, meterReadingTaken, tenantName, propertyName);
        System.out.println("LIST LENGTH: "+waterMeterDataList.size());
        Settings domain = settingsDao.findBySettingsKey("domain");
        Settings imagePath = settingsDao.findBySettingsKey("imagePath");
        List<WaterMeterDataDTO> waterMeterDataDTOList = new ArrayList<>();
        for(WaterMeterData waterMeterData : waterMeterDataList) {
            List<WaterMeterData> waterMeterDataList2 = meterReadingDao.getMeterWaterReadings(null, null, null, null, waterMeterData.getPropertyUnit().getPropertyNumberOrName());
            Collections.reverse(waterMeterDataList2);
            WaterMeterDataDTO waterMeterDataDTO;
            WaterMeterDataDTO.PreviousWaterMeterDataDTO previousWaterMeterDataDTO = new WaterMeterDataDTO.PreviousWaterMeterDataDTO();
            for(WaterMeterData waterMeterData1 : waterMeterDataList2) {
                if(waterMeterData1.getPropertyUnit().getPropertyNumberOrName().equalsIgnoreCase(waterMeterData.getPropertyUnit().getPropertyNumberOrName()) && waterMeterData1.getId() < waterMeterData.getId()){
                    previousWaterMeterDataDTO = waterMeterDataToPreviousWaterMeterDataDTO(waterMeterData1, domain.getValue(), "");
                    break;
                }
            }

            waterMeterDataDTO = waterMeterDataToWaterMeterDataDTO(waterMeterData, domain.getValue(), imagePath.getValue());
            waterMeterDataDTO.setPreviousWaterMeterData(previousWaterMeterDataDTO);
            waterMeterDataDTO.setWaterUnitsReading(waterMeterData.getWaterUnits());
            waterMeterDataDTOList.add(waterMeterDataDTO);
        }
        return waterMeterDataDTOList;
    }

    @Override
    public WaterMeterDataDTO getWaterMeterDataById(int id) {
        Settings domain = settingsDao.findBySettingsKey("domain");
        Settings filepath = settingsDao.findBySettingsKey("imagePath");

        WaterMeterData waterMeterData = meterReadingDao.getWaterMeterDataById(id);
        List<WaterMeterData> waterMeterDataList = meterReadingDao.getMeterWaterReadings(null, null, null, null, waterMeterData.getPropertyUnit().getPropertyNumberOrName());
        Collections.reverse(waterMeterDataList);
        WaterMeterDataDTO.PreviousWaterMeterDataDTO previousWaterMeterDataDTO = new WaterMeterDataDTO.PreviousWaterMeterDataDTO();
        for(WaterMeterData waterMeterData1 : waterMeterDataList) {
            if(waterMeterData1.getPropertyUnit().getPropertyNumberOrName().equalsIgnoreCase(waterMeterData.getPropertyUnit().getPropertyNumberOrName()) && waterMeterData1.getId() < waterMeterData.getId()){
                previousWaterMeterDataDTO = waterMeterDataToPreviousWaterMeterDataDTO(waterMeterData1, domain.getValue(), filepath.getValue());
                break;
            }
        }

        WaterMeterDataDTO waterMeterDataDTO;
        waterMeterDataDTO = waterMeterDataToWaterMeterDataDTO(meterReadingDao.getWaterMeterDataById(id), domain.getValue(), filepath.getValue());
        waterMeterDataDTO.setPreviousWaterMeterData(previousWaterMeterDataDTO);

        return waterMeterDataDTO;
    }

    @Transactional
    @Override
    public String deleteImage(int id) {
        return meterReadingDao.deleteImage(id);
    }


    public WaterMeterDataDTO waterMeterDataToWaterMeterDataDTO(WaterMeterData waterMeterData, String domain, String filePath) {
        WaterMeterDataDTO waterMeterDataDTO = new WaterMeterDataDTO();

        waterMeterDataDTO.setId(waterMeterData.getId());
        waterMeterDataDTO.setPropertyName(waterMeterData.getPropertyUnit().getPropertyNumberOrName());
        waterMeterDataDTO.setTenantName(waterMeterData.getTenant().getFullName());
        waterMeterDataDTO.setWaterUnitsReading(waterMeterData.getWaterUnits());
        waterMeterDataDTO.setPricePerUnit(waterMeterData.getPricePerUnit());
        waterMeterDataDTO.setMeterReadingDate(waterMeterData.getMeterReadingDate());
        waterMeterDataDTO.setMonth(waterMeterData.getMonth());
        waterMeterDataDTO.setYear(waterMeterData.getYear());
        if(waterMeterData.getWaterMeterImage().getName() != null) {
            waterMeterDataDTO.setImageName(domain +"/image/"+ waterMeterData.getWaterMeterImage().getName());
        } else {
            waterMeterDataDTO.setImageName(null);
        }
        waterMeterDataDTO.setImageId(waterMeterData.getWaterMeterImage().getId());
        return waterMeterDataDTO;
    }

    public WaterMeterDataDTO.PreviousWaterMeterDataDTO waterMeterDataToPreviousWaterMeterDataDTO(WaterMeterData waterMeterData, String domain, String filePath) {
        WaterMeterDataDTO.PreviousWaterMeterDataDTO waterMeterDataDTO = new WaterMeterDataDTO.PreviousWaterMeterDataDTO();
        waterMeterDataDTO.setId(waterMeterData.getId());
        waterMeterDataDTO.setPropertyName(waterMeterData.getPropertyUnit().getPropertyNumberOrName());
        waterMeterDataDTO.setTenantName(waterMeterData.getTenant().getFullName());
        waterMeterDataDTO.setWaterUnitsReading(waterMeterData.getWaterUnits());

        waterMeterDataDTO.setPricePerUnit(waterMeterData.getPricePerUnit());
        waterMeterDataDTO.setMeterReadingDate(waterMeterData.getMeterReadingDate());
        waterMeterDataDTO.setMonth(waterMeterData.getMonth());
        waterMeterDataDTO.setYear(waterMeterData.getYear());
        if(waterMeterData.getWaterMeterImage().getName() != null) {
            waterMeterDataDTO.setImageName(domain +"/image/"+ waterMeterData.getWaterMeterImage().getName());
        } else {
            waterMeterDataDTO.setImageName(null);
        }
        waterMeterDataDTO.setImageId(waterMeterData.getWaterMeterImage().getId());
        return waterMeterDataDTO;
    }
}


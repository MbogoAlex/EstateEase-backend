package com.propertymanagement.PropertyManagement.service;

import com.propertymanagement.PropertyManagement.dao.MeterReadingDao;
import com.propertymanagement.PropertyManagement.dao.PropertyUnitDao;
import com.propertymanagement.PropertyManagement.dao.SettingsDao;
import com.propertymanagement.PropertyManagement.dao.TenantDao;
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
import java.util.UUID;
import java.util.List;


@Service
@Slf4j
public class MeterReadingServiceImpl implements MeterReadingService {
    private MeterReadingDao meterReadingDao;
    private PropertyUnitDao propertyUnitDao;

    private SettingsDao settingsDao;

    private TenantDao tenantDao;


    @Autowired
    public MeterReadingServiceImpl(
            MeterReadingDao meterReadingDao,
            PropertyUnitDao propertyUnitDao,
            SettingsDao settingsDao,
            TenantDao tenantDao
    ) {
        this.meterReadingDao = meterReadingDao;
        this.propertyUnitDao = propertyUnitDao;
        this.settingsDao = settingsDao;
        this.tenantDao = tenantDao;
    }
    @Transactional
    @Override
    public List<WaterMeterDataDTO> initializeMeterReading() {
        List<Tenant> tenants = tenantDao.getActiveTenants();
        List<WaterMeterDataDTO> waterMeterDataDTOS = new ArrayList<>();
        for(Tenant tenant : tenants) {
            WaterMeterData waterMeterData = new WaterMeterData();
            WaterMeterImage waterMeterImage = new WaterMeterImage();
            waterMeterImage.setName(null);
            waterMeterData.setPropertyUnit(tenant.getPropertyUnit());
            waterMeterData.setTenant(tenant);
            waterMeterData.setMeterReadingTaken(false);
            waterMeterData.setWaterMeterImage(waterMeterImage);
            waterMeterData.setMonth(LocalDateTime.now().getMonth().toString().toUpperCase());
            waterMeterData.setYear(String.valueOf(LocalDateTime.now().getYear()));
            waterMeterImage.setWaterMeterData(waterMeterData);
            WaterMeterDataDTO waterMeterDataDTO;
            waterMeterDataDTO = waterMeterDataToWaterMeterDataDTO(meterReadingDao.addMeterWaterReading(waterMeterData));
            waterMeterDataDTOS.add(waterMeterDataDTO);
        }

        return waterMeterDataDTOS;
    }

    @Transactional
    @Override
    public WaterMeterDataDTO addMeterReading(MeterReadingDTO meterReadingDTO, MultipartFile image) throws IOException {
        WaterMeterData waterMeterData = meterReadingDao.getWaterMeterDataById(meterReadingDTO.getMeterDtTableId());
        Tenant tenant = waterMeterData.getTenant();
        PropertyUnit propertyUnit = waterMeterData.getPropertyUnit();
        Settings imagePath = settingsDao.findBySettingsKey("imagePath");
        String path = imagePath.getValue();
//        Settings domainName = settingsDao.findBySettingsKey("domain");
//        String domain = domainName.getValue();

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
        waterMeterData.setPricePerUnit(150.00);
        waterMeterData.setWaterMeterImage(waterMeterImage);
        waterMeterImage.setWaterMeterData(waterMeterData);


        propertyUnit.getWaterMeterData().add(waterMeterData);
//        propertyUnitDao.updateProperty(propertyUnit);

        return waterMeterDataToWaterMeterDataDTO(meterReadingDao.updateMeterReading(waterMeterData));
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
            meterReadingDao.deleteImage(oldImageId);
        } catch (IOException e) {
            System.err.println("Error deleting image: " + e.getMessage());
        }

        Tenant tenant = waterMeterData.getTenant();

        String path = result.getValue();
//        Settings domainName = settingsDao.findBySettingsKey("domain");
//        String domain = domainName.getValue();

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
        waterMeterData.setMonth(meterReadingDTO.getMonth());
        waterMeterData.setYear(meterReadingDTO.getYear());
        waterMeterData.setMeterReadingTaken(true);
        waterMeterData.setWaterMeterImage(waterMeterImage);

        return waterMeterDataToWaterMeterDataDTO(meterReadingDao.updateMeterReading(waterMeterData));
    }

    @Override
    public List<WaterMeterDataDTO> getMeterWaterReadings(String month, String year, Boolean meterReadingTaken, String tenantName, String propertyName) {
        String currentMonth = LocalDateTime.now().getMonth().toString();
        String currentYear = String.valueOf(LocalDateTime.now().getYear());

        String previousReadingMonth = "";
        String previousReadingYear = currentYear;

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


        List<WaterMeterData> waterMeterDataList = meterReadingDao.getMeterWaterReadings(month, year, meterReadingTaken, tenantName, propertyName);
        List<WaterMeterDataDTO> waterMeterDataDTOList = new ArrayList<>();
        for(WaterMeterData waterMeterData : waterMeterDataList) {
            WaterMeterDataDTO waterMeterDataDTO;
            WaterMeterDataDTO.PreviousWaterMeterDataDTO previousWaterMeterDataDTO = new WaterMeterDataDTO.PreviousWaterMeterDataDTO();
            if(waterMeterData.getMonth().equalsIgnoreCase(previousReadingMonth) && waterMeterData.getYear().equalsIgnoreCase(previousReadingYear)) {
                previousWaterMeterDataDTO = waterMeterDataToPreviousWaterMeterDataDTO(waterMeterData);
            }
            waterMeterDataDTO = waterMeterDataToWaterMeterDataDTO(waterMeterData);
            waterMeterDataDTO.setPreviousWaterMeterData(previousWaterMeterDataDTO);
            waterMeterDataDTOList.add(waterMeterDataDTO);
        }
        return waterMeterDataDTOList;
    }

    @Transactional
    @Override
    public String deleteImage(int id) {
        return meterReadingDao.deleteImage(id);
    }


    public WaterMeterDataDTO waterMeterDataToWaterMeterDataDTO(WaterMeterData waterMeterData) {
        WaterMeterDataDTO waterMeterDataDTO = new WaterMeterDataDTO();
        waterMeterDataDTO.setId(waterMeterData.getId());
        waterMeterDataDTO.setPropertyName(waterMeterData.getPropertyUnit().getPropertyNumberOrName());
        waterMeterDataDTO.setTenantName(waterMeterData.getTenant().getFullName());
        waterMeterDataDTO.setWaterUnits(waterMeterData.getWaterUnits());
        waterMeterDataDTO.setPricePerUnit(waterMeterData.getPricePerUnit());
        waterMeterDataDTO.setMeterReadingDate(waterMeterData.getMeterReadingDate());
        waterMeterDataDTO.setMonth(waterMeterData.getMonth());
        waterMeterDataDTO.setYear(waterMeterData.getYear());
        waterMeterDataDTO.setImageName(waterMeterData.getWaterMeterImage().getName());
        return waterMeterDataDTO;
    }

    public WaterMeterDataDTO.PreviousWaterMeterDataDTO waterMeterDataToPreviousWaterMeterDataDTO(WaterMeterData waterMeterData) {
        WaterMeterDataDTO.PreviousWaterMeterDataDTO waterMeterDataDTO = new WaterMeterDataDTO.PreviousWaterMeterDataDTO();
        waterMeterDataDTO.setId(waterMeterData.getId());
        waterMeterDataDTO.setPropertyName(waterMeterData.getPropertyUnit().getPropertyNumberOrName());
        waterMeterDataDTO.setTenantName(waterMeterData.getTenant().getFullName());
        waterMeterDataDTO.setWaterUnits(waterMeterData.getWaterUnits());
        waterMeterDataDTO.setPricePerUnit(waterMeterData.getPricePerUnit());
        waterMeterDataDTO.setMeterReadingDate(waterMeterData.getMeterReadingDate());
        waterMeterDataDTO.setMonth(waterMeterData.getMonth());
        waterMeterDataDTO.setYear(waterMeterData.getYear());
        waterMeterDataDTO.setImageName(waterMeterData.getWaterMeterImage().getName());
        return waterMeterDataDTO;
    }
}


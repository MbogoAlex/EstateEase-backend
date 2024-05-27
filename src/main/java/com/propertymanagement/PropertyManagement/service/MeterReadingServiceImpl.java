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
        List<PropertyUnit> propertyUnits = propertyUnitDao.fetchAllOccupiedUnits();
        List<WaterMeterDataDTO> waterMeterDataDTOS = new ArrayList<>();
        for(PropertyUnit propertyUnit : propertyUnits) {
            WaterMeterData waterMeterData = new WaterMeterData();
            waterMeterData.setPropertyUnit(propertyUnit);
            waterMeterData.setYear(LocalDateTime.now().getMonth().toString().toUpperCase());
            waterMeterData.setMonth(String.valueOf(LocalDateTime.now().getYear()));
            waterMeterDataDTOS.add(waterMeterDataToWaterMeterDataDTO(meterReadingDao.addMeterWaterReading(waterMeterData)));
        }

        return waterMeterDataDTOS;
    }

    @Transactional
    @Override
    public WaterMeterDataDTO addMeterReading(MeterReadingDTO meterReadingDTO, MultipartFile image) throws IOException {
        Tenant tenant = tenantDao.getTenantByTenantId(meterReadingDTO.getTenantId());
        Settings imagePath = settingsDao.findBySettingsKey("imagePath");
        String path = imagePath.getValue();
//        Settings domainName = settingsDao.findBySettingsKey("domain");
//        String domain = domainName.getValue();

        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename().replace(" ", "");
        String filePath = path + fileName;

        File uploadFile = new File(filePath);
        image.transferTo(uploadFile);

        PropertyUnit propertyUnit = propertyUnitDao.getPropertyByPropertyId(meterReadingDTO.getPropertyId());

        WaterMeterImage waterMeterImage = new WaterMeterImage();
        waterMeterImage.setName(fileName);

        WaterMeterData waterMeterData = new WaterMeterData();
        waterMeterData.setPropertyUnit(propertyUnit);
        waterMeterData.setTenant(tenant);
        waterMeterData.setWaterUnits(meterReadingDTO.getWaterUnits());
        waterMeterData.setMeterReadingDate(LocalDateTime.now());
        waterMeterData.setMonth(meterReadingDTO.getMonth().toUpperCase());
        waterMeterData.setYear(meterReadingDTO.getYear());
        waterMeterData.setMeterReadingTaken(true);
        waterMeterData.setWaterMeterImage(waterMeterImage);
        waterMeterImage.setWaterMeterData(waterMeterData);


        propertyUnit.getWaterMeterData().add(waterMeterData);
//        propertyUnitDao.updateProperty(propertyUnit);

        return waterMeterDataToWaterMeterDataDTO(meterReadingDao.updateMeterReading(waterMeterData));
    }
    @Transactional
    @Override
    public WaterMeterDataDTO updateMeterReading(MeterReadingDTO meterReadingDTO, MultipartFile image, int oldImageId, int meterReadingDataTableId) throws IOException {

        WaterMeterImage oldImage = meterReadingDao.getImageById(oldImageId);
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

        Tenant tenant = tenantDao.getTenantByTenantId(meterReadingDTO.getTenantId());

        String path = result.getValue();
//        Settings domainName = settingsDao.findBySettingsKey("domain");
//        String domain = domainName.getValue();

        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename().replace(" ", "");
        String filePath = path + fileName;

        File uploadFile = new File(filePath);
        image.transferTo(uploadFile);

        PropertyUnit propertyUnit = propertyUnitDao.getPropertyByPropertyId(meterReadingDTO.getPropertyId());

        WaterMeterImage waterMeterImage = new WaterMeterImage();
        waterMeterImage.setName(fileName);

        WaterMeterData waterMeterData = meterReadingDao.getMeterWaterReadingById(meterReadingDataTableId);
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
    public List<WaterMeterDataDTO> getMeterWaterReadings(String month, String year, Boolean meterReadingTaken) {
        List<WaterMeterData> waterMeterDataList = meterReadingDao.getMeterWaterReadings(month, year, meterReadingTaken);
        List<WaterMeterDataDTO> waterMeterDataDTOList = new ArrayList<>();
        for(WaterMeterData waterMeterData : waterMeterDataList) {
            waterMeterDataDTOList.add(waterMeterDataToWaterMeterDataDTO(waterMeterData));
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


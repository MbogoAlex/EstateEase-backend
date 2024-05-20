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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;


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
        waterMeterData.setWaterUnits(meterReadingDTO.getWaterUnits());
        waterMeterData.setMeterReadingDate(LocalDateTime.now());
        waterMeterData.setMeterReadingTaken(true);
        waterMeterData.setWaterMeterImage(waterMeterImage);

        return waterMeterDataToWaterMeterDataDTO(meterReadingDao.addMeterWaterReading(waterMeterData), fileName, tenant);
    }

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
        waterMeterData.setWaterUnits(meterReadingDTO.getWaterUnits());
        waterMeterData.setMeterReadingDate(LocalDateTime.now());
        waterMeterData.setMeterReadingTaken(true);
        waterMeterData.setWaterMeterImage(waterMeterImage);

        return waterMeterDataToWaterMeterDataDTO(meterReadingDao.updateMeterReading(waterMeterData), fileName, tenant);
    }

    @Override
    public String deleteImage(int id) {
        return meterReadingDao.deleteImage(id);
    }


    public WaterMeterDataDTO waterMeterDataToWaterMeterDataDTO(WaterMeterData waterMeterData, String imageName, Tenant tenant) {
        WaterMeterDataDTO waterMeterDataDTO = new WaterMeterDataDTO();
        waterMeterDataDTO.setPropertyName(waterMeterData.getPropertyUnit().getPropertyNumberOrName());
        waterMeterDataDTO.setTenantName(tenant.getFullName());
        waterMeterDataDTO.setWaterUnits(waterMeterData.getWaterUnits());
        waterMeterDataDTO.setPricePerUnit(waterMeterData.getPricePerUnit());
        waterMeterDataDTO.setMeterReadingDate(waterMeterData.getMeterReadingDate());
        waterMeterDataDTO.setImageName(imageName);
        return waterMeterDataDTO;
    }
}


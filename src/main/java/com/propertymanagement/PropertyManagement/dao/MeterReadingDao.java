package com.propertymanagement.PropertyManagement.dao;

import com.propertymanagement.PropertyManagement.entity.WaterMeterData;
import com.propertymanagement.PropertyManagement.entity.WaterMeterImage;
import org.springframework.web.multipart.MultipartFile;

public interface MeterReadingDao {
    WaterMeterData addMeterWaterReading(WaterMeterData waterMeterData);
    WaterMeterData getMeterWaterReadingById(int id);

    String deleteImage(int id);

    WaterMeterData updateMeterReading(WaterMeterData waterMeterData);

    WaterMeterImage getImageById(int id);

}

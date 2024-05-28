package com.propertymanagement.PropertyManagement.dao;

import com.propertymanagement.PropertyManagement.entity.WaterMeterData;
import com.propertymanagement.PropertyManagement.entity.WaterMeterImage;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface MeterReadingDao {
    WaterMeterData addMeterWaterReading(WaterMeterData waterMeterData);
    WaterMeterData getMeterWaterReadingById(int id);

    List<WaterMeterData> getMeterWaterReadings(String month, String year, Boolean meterReadingTaken, String tenantName, String propertyName);

    String deleteImage(int id);

    WaterMeterData updateMeterReading(WaterMeterData waterMeterData);

    WaterMeterImage getImageById(int id);

    WaterMeterData getWaterMeterDataById(int id);

}

package com.propertymanagement.PropertyManagement.service;

import com.propertymanagement.PropertyManagement.dto.MeterReadingDTO;
import com.propertymanagement.PropertyManagement.dto.WaterMeterDataDTO;
import com.propertymanagement.PropertyManagement.entity.WaterMeterData;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

import java.io.IOException;

public interface MeterReadingService {
    List<WaterMeterDataDTO> initializeMeterReading();
    WaterMeterDataDTO addMeterReading(MeterReadingDTO meterReadingDTO, MultipartFile image) throws IOException;

    WaterMeterDataDTO updateMeterReading(MeterReadingDTO meterReadingDTO, MultipartFile image, int oldImageId, int meterReadingDataTableId) throws IOException;

    List<WaterMeterDataDTO> getMeterWaterReadings(String month, String year, Boolean meterReadingTaken);

    String deleteImage(int id);
}

package com.propertymanagement.PropertyManagement.service;

import com.propertymanagement.PropertyManagement.dto.MeterReadingDTO;
import com.propertymanagement.PropertyManagement.dto.WaterMeterDataDTO;
import com.propertymanagement.PropertyManagement.entity.WaterMeterData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MeterReadingService {
    WaterMeterDataDTO addMeterReading(MeterReadingDTO meterReadingDTO, MultipartFile image) throws IOException;

    WaterMeterDataDTO updateMeterReading(MeterReadingDTO meterReadingDTO, MultipartFile image, int oldImageId, int meterReadingDataTableId) throws IOException;

    String deleteImage(int id);
}

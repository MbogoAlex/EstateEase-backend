package com.propertymanagement.PropertyManagement.controller;

import com.propertymanagement.PropertyManagement.dto.MeterReadingDTO;
import com.propertymanagement.PropertyManagement.dto.Response;
import com.propertymanagement.PropertyManagement.dto.WaterMeterDataDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;

public interface MeterReadingController {
    ResponseEntity<Response> addMeterReading(MeterReadingDTO meterReadingDTO, MultipartFile image) throws IOException;

    ResponseEntity<Response> updateMeterReading(MeterReadingDTO meterReadingDTO, MultipartFile image, int oldImageId) throws IOException;

    ResponseEntity<Response> initializeMeterReading();

    ResponseEntity<Response> getMeterWaterReadings(String month, String year, Boolean meterReadingTaken, String tenantName, String propertyName);

    String deleteImage(int id);
}

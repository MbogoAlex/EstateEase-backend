package com.propertymanagement.PropertyManagement.controller;

import com.propertymanagement.PropertyManagement.dto.MeterReadingDTO;
import com.propertymanagement.PropertyManagement.dto.Response;
import com.propertymanagement.PropertyManagement.service.MeterReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

import static java.util.Map.of;
@RestController
@RequestMapping("/api")
public class MeterReadingControllerImpl implements MeterReadingController{

    private MeterReadingService meterReadingService;
    @Autowired
    public MeterReadingControllerImpl(MeterReadingService meterReadingService) {
        this.meterReadingService = meterReadingService;
    }
    @PutMapping("/meterreading")
    @Override
    public ResponseEntity<Response> addMeterReading(
            @RequestPart("data") MeterReadingDTO meterReadingDTO,
            @RequestPart(value = "image") MultipartFile image
    ) throws IOException {
        return buildResponse("waterMeter", meterReadingService.addMeterReading(meterReadingDTO, image), "Added successfully", HttpStatus.CREATED);
    }
    @PutMapping("/meterreading/oldImageId={oldImageId}/meterReadingDataTableId={meterReadingDataTableId}")
    @Override
    public ResponseEntity<Response> updateMeterReading(
            @RequestPart("data") MeterReadingDTO meterReadingDTO,
            @RequestPart(value = "image") MultipartFile image,
            @PathVariable("oldImageId") int oldImageId,
            @PathVariable("meterReadingDataTableId") int meterReadingDataTableId
    ) throws IOException {
        return buildResponse("waterMeter", meterReadingService.updateMeterReading(meterReadingDTO, image, oldImageId, meterReadingDataTableId), "Update successful", HttpStatus.CREATED);
    }
    @PostMapping("/meterreading/initialize")
    @Override
    public ResponseEntity<Response> initializeMeterReading() {
        return buildResponse("waterMeter", meterReadingService.initializeMeterReading(), "Success", HttpStatus.CREATED);
    }
    @GetMapping("meterreading/all/month={month}/year={year}/meterReadingTaken={meterReadingTaken}")
    @Override
    public ResponseEntity<Response> getMeterWaterReadings(@PathVariable("month") String month, @PathVariable("year") String year, @PathVariable("meterReadingTaken") Boolean meterReadingTaken) {
        return buildResponse("waterMeter", meterReadingService.getMeterWaterReadings(month, year, meterReadingTaken), "Fetching successful", HttpStatus.OK);
    }

    @DeleteMapping("/meterreading/imageId={imageId}")
    @Override
    public String deleteImage(@PathVariable("imageId") int id) {
        return meterReadingService.deleteImage(id);
    }

    private ResponseEntity<Response> buildResponse(String desc, Object data, String message, HttpStatus status) {
        return ResponseEntity.status(status)
                .body(Response.builder()
                        .timestamp(LocalDateTime.now())
                        .data(data == null ? null : of(desc, data))
                        .message(message)
                        .status(status)
                        .statusCode(status.value())
                        .build());
    }
}

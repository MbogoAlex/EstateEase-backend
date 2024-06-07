package com.propertymanagement.PropertyManagement.controller;

import com.propertymanagement.PropertyManagement.dto.AmenityRequestDTO;
import com.propertymanagement.PropertyManagement.dto.AmenityResponseDTO;
import com.propertymanagement.PropertyManagement.dto.Response;
import com.propertymanagement.PropertyManagement.service.AmenityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

import static java.util.Map.of;
@RestController
@RequestMapping("/api/")
public class AmenityControllerImpl implements AmenityController{
    private final AmenityService amenityService;
    @Autowired
    public AmenityControllerImpl(AmenityService amenityService) {
        this.amenityService = amenityService;
    }
    @PostMapping("amenity")
    @Override
    public ResponseEntity<Response> addAmenity(
            @RequestPart("data") AmenityRequestDTO amenityRequestDTO,
            @RequestPart("images") MultipartFile[] images
    ) throws IOException {
        return buildResponse("amenity", amenityService.addAmenity(amenityRequestDTO, images), "Amenity added", HttpStatus.CREATED);
    }
    @PutMapping("amenity/{id}")
    @Override
    public ResponseEntity<Response> updateAmenity(
            @RequestPart("data") AmenityRequestDTO amenityRequestDTO,
            @RequestPart("images") MultipartFile[] images,
            @RequestPart("oldimages") AmenityResponseDTO.AmenityResponseImage[] oldImages,
            @PathVariable("id") int amenityId
    ) throws IOException {
        return buildResponse("amenity", amenityService.updateAmenity(amenityRequestDTO, images, oldImages, amenityId), "Amenity updated", HttpStatus.CREATED);
    }
    @DeleteMapping("amenity/{id}")
    @Override
    public ResponseEntity<Response> deleteAmenity(@PathVariable("id") int amenityId) {
        return buildResponse("amenity", amenityService.deleteAmenity(amenityId), "Deleted successfully", HttpStatus.OK);
    }
    @GetMapping("amenity")
    @Override
    public ResponseEntity<Response> getAllAmenities() {
        return buildResponse("amenities", amenityService.getAllAmenities(), "Amenities fetched", HttpStatus.OK);
    }
    @GetMapping("amenity/{id}")
    @Override
    public ResponseEntity<Response> getAmenity(@PathVariable("id") int id) {
        return buildResponse("amenity", amenityService.getAmenity(id), "Amenity fetched", HttpStatus.OK);
    }
    @DeleteMapping("amenity/id/image")
    @Override
    public ResponseEntity<Response> deleteImage(@PathVariable("id") int id) {
        return buildResponse("amenity", amenityService.deleteImage(id), "Image deleted", HttpStatus.OK);
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

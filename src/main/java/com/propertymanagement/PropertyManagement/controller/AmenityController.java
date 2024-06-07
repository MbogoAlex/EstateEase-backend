package com.propertymanagement.PropertyManagement.controller;

import com.propertymanagement.PropertyManagement.dto.AmenityRequestDTO;
import com.propertymanagement.PropertyManagement.dto.AmenityResponseDTO;
import com.propertymanagement.PropertyManagement.dto.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AmenityController {
    ResponseEntity<Response> addAmenity(AmenityRequestDTO amenityRequestDTO, MultipartFile[] images) throws IOException;

    ResponseEntity<Response> updateAmenity(AmenityRequestDTO amenityRequestDTO, MultipartFile[] images, AmenityResponseDTO.AmenityResponseImage[] oldImages, int amenityId) throws IOException;

    ResponseEntity<Response> deleteAmenity(int amenityId);

    ResponseEntity<Response> getAllAmenities();
}

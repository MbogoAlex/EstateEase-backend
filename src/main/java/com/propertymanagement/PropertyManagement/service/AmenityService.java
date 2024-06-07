package com.propertymanagement.PropertyManagement.service;

import com.propertymanagement.PropertyManagement.dto.AmenityRequestDTO;
import com.propertymanagement.PropertyManagement.dto.AmenityResponseDTO;
import com.propertymanagement.PropertyManagement.entity.Amenity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AmenityService {
    AmenityResponseDTO addAmenity(AmenityRequestDTO amenityRequestDTO, MultipartFile[] images) throws IOException;

    AmenityResponseDTO updateAmenity(AmenityRequestDTO amenityRequestDTO, MultipartFile[] images, AmenityResponseDTO.AmenityResponseImage[] oldImages, int amenityId) throws IOException;

    List<AmenityResponseDTO> getAllAmenities();

    String deleteAmenity(int amenityId);
}

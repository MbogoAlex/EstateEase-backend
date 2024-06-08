package com.propertymanagement.PropertyManagement.service;

import com.propertymanagement.PropertyManagement.dto.AmenityRequestDTO;
import com.propertymanagement.PropertyManagement.dto.AmenityResponseDTO;
import com.propertymanagement.PropertyManagement.entity.Amenity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AmenityService {
    AmenityResponseDTO addAmenity(AmenityRequestDTO amenityRequestDTO, MultipartFile[] images) throws IOException;

    AmenityResponseDTO updateAmenityWithImages(AmenityRequestDTO amenityRequestDTO, MultipartFile[] images, int amenityId) throws IOException;
    AmenityResponseDTO updateAmenityWithoutImages(AmenityRequestDTO amenityRequestDTO, int amenityId);

    List<AmenityResponseDTO> getAllAmenities();

    AmenityResponseDTO getAmenity(int id);
    List<AmenityResponseDTO> getFilteredAmenity(String value);

    String deleteAmenity(int amenityId);

    String deleteImage(int imageId);
}

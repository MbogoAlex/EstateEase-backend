package com.propertymanagement.PropertyManagement.service;

import com.propertymanagement.PropertyManagement.dao.AmenityDao;
import com.propertymanagement.PropertyManagement.dao.SettingsDao;
import com.propertymanagement.PropertyManagement.dto.AmenityRequestDTO;
import com.propertymanagement.PropertyManagement.dto.AmenityResponseDTO;
import com.propertymanagement.PropertyManagement.entity.Amenity;
import com.propertymanagement.PropertyManagement.entity.AmenityImage;
import com.propertymanagement.PropertyManagement.entity.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

@Service
public class AmenityServiceImpl implements AmenityService{
    private final SettingsDao settingsDao;
    private final AmenityDao amenityDao;
    @Autowired
    public AmenityServiceImpl(SettingsDao settingsDao, AmenityDao amenityDao) {
        this.settingsDao = settingsDao;
        this.amenityDao = amenityDao;
    }
    @Transactional
    @Override
    public AmenityResponseDTO addAmenity(AmenityRequestDTO amenityRequestDTO, MultipartFile[] images) throws IOException {
        Amenity amenity = new Amenity();
        Settings imagePath = settingsDao.findBySettingsKey("imagePath");
        String path = imagePath.getValue();
        Settings domainName = settingsDao.findBySettingsKey("domain");
        String domain = domainName.getValue();

        amenity.setAmenityName(amenityRequestDTO.getAmenityName());
        amenity.setProviderName(amenityRequestDTO.getProviderName());
        amenity.setAmenityDescription(amenityRequestDTO.getAmenityDescription());
        amenity.setProviderPhoneNumber(amenityRequestDTO.getProviderPhoneNumber());
        amenity.setProviderEmail(amenityRequestDTO.getProviderEmail());
        amenity.setAddedAt(LocalDateTime.now());
        amenity.setAddedBy(amenityRequestDTO.getAddedBy());
        amenity.setPManagerId(amenityRequestDTO.getPropertyManagerId());

        for(MultipartFile image : images) {
            AmenityImage amenityImage = new AmenityImage();
            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename().replace(" ", "");
            String filePath = path + fileName;
            File uploadFile = new File(filePath);
            image.transferTo(uploadFile);

            amenityImage.setName(fileName);

            amenity.getImages().add(amenityImage);
            amenityImage.setAmenity(amenity);
        }

        return mapAmenityToAmenityDTO(amenityDao.addAmenity(amenity), domain);
    }
    @Transactional
    @Override
    public AmenityResponseDTO updateAmenity(AmenityRequestDTO amenityRequestDTO, MultipartFile[] images, AmenityResponseDTO.AmenityResponseImage[] oldImages, int amenityId) throws IOException {
        Amenity amenity = amenityDao.getById(amenityId);
        Settings imagePath = settingsDao.findBySettingsKey("imagePath");
        String path = imagePath.getValue();
        Settings domainName = settingsDao.findBySettingsKey("domain");
        String domain = domainName.getValue();

        for(AmenityResponseDTO.AmenityResponseImage image : oldImages) {
            AmenityImage oldImage = amenityDao.getImage(image.getId());
            String oldImagePath = path + oldImage.getName();
            Path oldPath = Paths.get(oldImagePath);

            try {
                Files.delete(oldPath);
                amenityDao.deleteImage(image.getId());
            } catch (IOException e) {
                System.err.println("Error deleting image: " + e.getMessage());
            }

        }

        amenity.setAmenityName(amenityRequestDTO.getAmenityName());
        amenity.setProviderName(amenityRequestDTO.getProviderName());
        amenity.setAmenityDescription(amenityRequestDTO.getAmenityDescription());
        amenity.setProviderPhoneNumber(amenityRequestDTO.getProviderPhoneNumber());
        amenity.setProviderEmail(amenityRequestDTO.getProviderEmail());
        amenity.setAddedAt(LocalDateTime.now());
        amenity.setAddedBy(amenityRequestDTO.getAddedBy());
        amenity.setPManagerId(amenityRequestDTO.getPropertyManagerId());

        for(MultipartFile image : images) {
            AmenityImage amenityImage = new AmenityImage();
            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename().replace(" ", "");
            String filePath = path + fileName;
            File uploadFile = new File(filePath);
            image.transferTo(uploadFile);

            amenityImage.setName(fileName);

            amenity.getImages().add(amenityImage);
            amenityImage.setAmenity(amenity);
        }
        return mapAmenityToAmenityDTO(amenityDao.updateAmenity(amenity), domain);
    }

    @Override
    public List<AmenityResponseDTO> getAllAmenities() {
        List<Amenity> amenities = amenityDao.getAllAmenities();
        Settings settings = settingsDao.findBySettingsKey("domain");
        List<AmenityResponseDTO> mappedAmenities = new ArrayList<>();
        for(Amenity amenity : amenities) {
            mappedAmenities.add(mapAmenityToAmenityDTO(amenity, settings.getValue()));
        }
        return mappedAmenities;
    }

    @Override
    public AmenityResponseDTO getAmenity(int id) {
        Settings settings = settingsDao.findBySettingsKey("domain");
        return mapAmenityToAmenityDTO(amenityDao.getAmenity(id), settings.getValue());
    }

    @Transactional
    @Override
    public String deleteAmenity(int amenityId) {
        Settings imagePath = settingsDao.findBySettingsKey("imagePath");
        String path = imagePath.getValue();

        Amenity amenity = amenityDao.getById(amenityId);
        if(!amenity.getImages().isEmpty()) {
            for(AmenityImage image : amenity.getImages()) {
                String oldImagePath = path + image.getName();
                Path oldPath = Paths.get(oldImagePath);
                try {
                    Files.delete(oldPath);
                    amenityDao.deleteImage(image.getId());
                } catch (IOException e) {
                    System.err.println("Error deleting image: " + e.getMessage());
                }
            }
        }

        return amenityDao.deleteAmenity(amenityId);
    }

    @Override
    public String deleteImage(int imageId) {
        Settings imagePath = settingsDao.findBySettingsKey("imagePath");
        AmenityImage amenityImage = amenityDao.getAmenityImage(imageId);
        String path = imagePath.getValue();

        String oldImagePath = path + amenityImage.getName();
        Path oldPath = Paths.get(oldImagePath);

        try {
            Files.delete(oldPath);
            amenityDao.deleteImage(amenityImage.getId());
        } catch (IOException e) {
            System.err.println("Error deleting image: " + e.getMessage());
        }

        return "Amenity image deleted";
    }

    AmenityResponseDTO mapAmenityToAmenityDTO(Amenity amenity, String domain) {
        AmenityResponseDTO amenityResponseDTO = new AmenityResponseDTO();
        List<AmenityResponseDTO.AmenityResponseImage> images = new ArrayList<>();
        amenityResponseDTO.setAmenityId(amenity.getId());
        amenityResponseDTO.setAmenityName(amenity.getAmenityName());
        amenityResponseDTO.setAmenityDescription(amenity.getAmenityDescription());
        amenityResponseDTO.setProviderName(amenity.getProviderName());
        amenityResponseDTO.setProviderPhoneNumber(amenity.getProviderPhoneNumber());
        amenityResponseDTO.setProviderEmail(amenity.getProviderEmail());
        amenityResponseDTO.setAddedBy(amenity.getAddedBy());
        amenityResponseDTO.setPManagerId(amenity.getPManagerId());
        for(AmenityImage amenityImage : amenity.getImages()) {
            AmenityResponseDTO.AmenityResponseImage image = new AmenityResponseDTO.AmenityResponseImage();
            image.setId(amenityImage.getId());
            image.setName(domain + "/image/" + amenityImage.getName());
            images.add(image);
        }
        amenityResponseDTO.getImages().addAll(images);

        return amenityResponseDTO;
    }
}

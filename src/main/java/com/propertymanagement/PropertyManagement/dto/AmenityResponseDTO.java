package com.propertymanagement.PropertyManagement.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmenityResponseDTO {
    private int amenityId;
    private String amenityName;
    private String amenityDescription;
    private String providerName;
    private String providerPhoneNumber;
    private String providerEmail;
    private String addedBy;
    private int pManagerId;
    private List<AmenityResponseImage> images = new ArrayList<>();
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AmenityResponseImage{
        private int id;
        private String name;
    }
}

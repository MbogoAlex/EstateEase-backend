package com.propertymanagement.PropertyManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmenityRequestDTO {
    private String amenityName;
    private String amenityDescription;
    private String providerName;
    private String providerPhoneNumber;
    private String providerEmail;
    private String addedBy;
    private int propertyManagerId;
}

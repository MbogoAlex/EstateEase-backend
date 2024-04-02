package com.propertymanagement.PropertyManagement.dto.pManagerResponse;

import com.propertymanagement.PropertyManagement.dto.propertyResponse.PropertyTenantDTO;
import com.propertymanagement.PropertyManagement.dto.tenantResponse.TenantPropertyDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PManagerResponseDTO {
    private int pManagerId;
    private String fullName;
    private String nationalIdOrPassportNumber;
    private String phoneNumber;
    private String email;
    private String propertyManagerAddedAt;
    private List<TenantPropertyDTO> propertyUnits = new ArrayList<>();
    private List<PropertyTenantDTO> tenants = new ArrayList<>();
}

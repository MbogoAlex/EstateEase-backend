package com.propertymanagement.PropertyManagement.dto.propertyResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyTenantDTO {
    private int tenantId;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String tenantAddedAt;
    private Boolean tenantActive;

}

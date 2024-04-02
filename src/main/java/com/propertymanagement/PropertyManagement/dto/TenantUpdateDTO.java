package com.propertymanagement.PropertyManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenantUpdateDTO {
    private int tenantId;
    private String fullName;
    private String nationalIdOrPassportNumber;
    private String phoneNumber;
    private String email;

    private String password;

    private int roleId;

    private int propertyUnitId;

    private Double penaltyPerDay;
}

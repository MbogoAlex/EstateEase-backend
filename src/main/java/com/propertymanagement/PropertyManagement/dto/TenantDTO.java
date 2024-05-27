package com.propertymanagement.PropertyManagement.dto;

import com.propertymanagement.PropertyManagement.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenantDTO {
    private String fullName;
    private String nationalIdOrPassportNumber;
    private String phoneNumber;
    private String email;

    private String password;

    private int roleId;

    private int propertyUnitId;
    private int tenantAddedByPManagerId;

    private Double penaltyPerDay;

    class PManagerDTO {

    }

}


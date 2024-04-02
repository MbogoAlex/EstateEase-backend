package com.propertymanagement.PropertyManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenantLoginDTO {
    private String tenantRoomNameOrNumber;
    private String tenantPhoneNumber;
    private String tenantPassword;
}

package com.propertymanagement.PropertyManagement.dto.tenantResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenantPropertyDTO {
    private int propertyUnitId;
    private String rooms;
    private String propertyNumberOrName;
    private String propertyDescription;
    private Double monthlyRent;
}

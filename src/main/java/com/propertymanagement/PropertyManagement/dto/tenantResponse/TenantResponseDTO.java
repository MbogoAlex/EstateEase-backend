package com.propertymanagement.PropertyManagement.dto.tenantResponse;

import com.propertymanagement.PropertyManagement.entity.RentPayment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenantResponseDTO {
    private int tenantId;
    private String fullName;
    private String nationalIdOrPassportNumber;
    private String phoneNumber;
    private String email;
    private String tenantAddedAt;
    private Boolean tenantActive;
    private TenantPropertyDTO propertyUnit;
    private List<RentPayment> rentPayments = new ArrayList<>();
}

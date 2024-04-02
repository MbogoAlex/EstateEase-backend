package com.propertymanagement.PropertyManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentPaymentOverviewDTO {
    private Double totalExpectedRent;
    private int totalUnits;
    private Double paidAmount;
    private int clearedUnits;
    private Double deficit;
    private int unclearedUnits;
}

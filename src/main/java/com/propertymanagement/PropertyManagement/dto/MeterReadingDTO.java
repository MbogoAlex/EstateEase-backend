package com.propertymanagement.PropertyManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeterReadingDTO {
    private Integer meterDtTableId;
    private Double waterUnits;
    private String month;
    private String year;
}

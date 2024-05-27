package com.propertymanagement.PropertyManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeterReadingDTO {
    private Integer propertyId;
    private Integer tenantId;
    private Double waterUnits;
    private String month;
    private String year;
}

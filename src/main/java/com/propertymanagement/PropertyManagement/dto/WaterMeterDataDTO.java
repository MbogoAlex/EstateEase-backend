package com.propertymanagement.PropertyManagement.dto;

import com.propertymanagement.PropertyManagement.entity.PropertyUnit;
import com.propertymanagement.PropertyManagement.entity.WaterMeterImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WaterMeterDataDTO {
    private Integer id;
    private String propertyName;
    private String tenantName;
    private Double waterUnits;
    private Double pricePerUnit;
    private LocalDateTime meterReadingDate;
    private String month;
    private String year;
    private String imageName;
    private Integer imageId;
    private PreviousWaterMeterDataDTO previousWaterMeterData;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PreviousWaterMeterDataDTO {
        private Integer id;
        private String propertyName;
        private String tenantName;
        private Double waterUnits;
        private Double pricePerUnit;
        private LocalDateTime meterReadingDate;
        private String month;
        private String year;
        private String imageName;
        private Integer imageId;
    }

}



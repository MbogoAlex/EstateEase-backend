package com.propertymanagement.PropertyManagement.dto;

import com.propertymanagement.PropertyManagement.entity.Tenant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentPaymentDetailsDTO {
    private int rentPaymentTblId;
    private String transactionId;
    private Double monthlyRent;
    private Month month;
    private Year year;
    private LocalDate dueDate;
    private Boolean rentPaymentStatus;
    private Boolean penaltyActive;
    private long daysLate;
    private Double penaltyPerDay;
    private Double paidAmount;
    private LocalDateTime paidAt;
    private Boolean paidLate;
    private TenantDataDTO tenant;
    private String unitName;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TenantDataDTO {
        private int tenantId;
        private String fullName;
        private String nationalIdOrPassportNumber;
        private String phoneNumber;
        private String email;
        private LocalDateTime tenantAddedAt;
        private Boolean tenantActive;
    }
}

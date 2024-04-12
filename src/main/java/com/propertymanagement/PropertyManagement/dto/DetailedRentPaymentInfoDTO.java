package com.propertymanagement.PropertyManagement.dto;

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
public class DetailedRentPaymentInfoDTO {
    private LocalDate dueDate;
    private Month month;
    private Double monthlyRent;
    private Double paidAmount;
    private LocalDateTime paidAt;
    private Boolean paidLate;
    private Boolean rentPaymentStatus;
    private Boolean penaltyActive;
    private Double penaltyPerDay;
    private String transactionId;
    private Year year;
    private String propertyNumberOrName;
    private Integer numberOfRooms;
    private Integer tenantId;
    private String email;
    private String fullName;
    private String nationalIdOrPassport;
    private String phoneNumber;
    private LocalDateTime tenantAddedAt;
    private Boolean tenantActive;
}

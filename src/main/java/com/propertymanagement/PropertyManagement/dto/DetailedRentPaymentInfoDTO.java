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
    private int rentPaymentTblId;
    private LocalDate dueDate;
    private Month month;
    private Double monthlyRent;
    private Double paidAmount;
    private LocalDateTime paidAt;
    private Boolean paidLate;
    private Long daysLate;
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

    private Double waterUnits;

    private Double pricePerUnit;
    private LocalDateTime meterReadingDate;

    private String imageFile;

    public DetailedRentPaymentInfoDTO(int rentPaymentTblId, LocalDate dueDate, Month month, Double monthlyRent, Double paidAmount, LocalDateTime paidAt, Boolean paidLate, Boolean rentPaymentStatus, Boolean penaltyActive, Double penaltyPerDay, String transactionId, Year year, String propertyNumberOrName, Integer numberOfRooms, Integer tenantId, String email, String fullName, String nationalIdOrPassport, String phoneNumber, LocalDateTime tenantAddedAt, Boolean tenantActive) {
        this.rentPaymentTblId = rentPaymentTblId;
        this.dueDate = dueDate;
        this.month = month;
        this.monthlyRent = monthlyRent;
        this.paidAmount = paidAmount;
        this.paidAt = paidAt;
        this.paidLate = paidLate;
        this.rentPaymentStatus = rentPaymentStatus;
        this.penaltyActive = penaltyActive;
        this.penaltyPerDay = penaltyPerDay;
        this.transactionId = transactionId;
        this.year = year;
        this.propertyNumberOrName = propertyNumberOrName;
        this.numberOfRooms = numberOfRooms;
        this.tenantId = tenantId;
        this.email = email;
        this.fullName = fullName;
        this.nationalIdOrPassport = nationalIdOrPassport;
        this.phoneNumber = phoneNumber;
        this.tenantAddedAt = tenantAddedAt;
        this.tenantActive = tenantActive;
    }

    public DetailedRentPaymentInfoDTO(int rentPaymentTblId, LocalDate dueDate, Month month, Double monthlyRent, Double paidAmount, LocalDateTime paidAt, Boolean paidLate, Boolean rentPaymentStatus, Boolean penaltyActive, Double penaltyPerDay, String transactionId, Year year, String propertyNumberOrName, Integer numberOfRooms, Integer tenantId, String email, String fullName, String nationalIdOrPassport, String phoneNumber, LocalDateTime tenantAddedAt, Boolean tenantActive, Double waterUnits, Double pricePerUnit, LocalDateTime meterReadingDate, String imageFile) {
        this.rentPaymentTblId = rentPaymentTblId;
        this.dueDate = dueDate;
        this.month = month;
        this.monthlyRent = monthlyRent;
        this.paidAmount = paidAmount;
        this.paidAt = paidAt;
        this.paidLate = paidLate;
        this.rentPaymentStatus = rentPaymentStatus;
        this.penaltyActive = penaltyActive;
        this.penaltyPerDay = penaltyPerDay;
        this.transactionId = transactionId;
        this.year = year;
        this.propertyNumberOrName = propertyNumberOrName;
        this.numberOfRooms = numberOfRooms;
        this.tenantId = tenantId;
        this.email = email;
        this.fullName = fullName;
        this.nationalIdOrPassport = nationalIdOrPassport;
        this.phoneNumber = phoneNumber;
        this.tenantAddedAt = tenantAddedAt;
        this.tenantActive = tenantActive;
        this.waterUnits = waterUnits;
        this.pricePerUnit = pricePerUnit;
        this.meterReadingDate = meterReadingDate;
        this.imageFile = imageFile;
    }

    @Override
    public String toString() {
        return "DetailedRentPaymentInfoDTO{" +
                "rentPaymentTblId=" + rentPaymentTblId +
                ", dueDate=" + dueDate +
                ", month=" + month +
                ", monthlyRent=" + monthlyRent +
                ", paidAmount=" + paidAmount +
                ", paidAt=" + paidAt +
                ", paidLate=" + paidLate +
                ", daysLate=" + daysLate +
                ", rentPaymentStatus=" + rentPaymentStatus +
                ", penaltyActive=" + penaltyActive +
                ", penaltyPerDay=" + penaltyPerDay +
                ", transactionId='" + transactionId + '\'' +
                ", year=" + year +
                ", propertyNumberOrName='" + propertyNumberOrName + '\'' +
                ", numberOfRooms=" + numberOfRooms +
                ", tenantId=" + tenantId +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", nationalIdOrPassport='" + nationalIdOrPassport + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", tenantAddedAt=" + tenantAddedAt +
                ", tenantActive=" + tenantActive +
                ", waterUnits=" + waterUnits +
                ", pricePerUnit=" + pricePerUnit +
                ", meterReadingDate=" + meterReadingDate +
                ", imageFile='" + imageFile + '\'' +
                '}';
    }
}

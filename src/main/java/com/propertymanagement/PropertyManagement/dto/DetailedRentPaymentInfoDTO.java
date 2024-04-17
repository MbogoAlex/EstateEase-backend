package com.propertymanagement.PropertyManagement.dto;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;

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

    public int getRentPaymentTblId() {
        return rentPaymentTblId;
    }

    public void setRentPaymentTblId(int rentPaymentTblId) {
        this.rentPaymentTblId = rentPaymentTblId;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Double getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(Double monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public Boolean getPaidLate() {
        return paidLate;
    }

    public void setPaidLate(Boolean paidLate) {
        this.paidLate = paidLate;
    }

    public Long getDaysLate() {
        return daysLate;
    }

    public void setDaysLate(Long daysLate) {
        this.daysLate = daysLate;
    }

    public Boolean getRentPaymentStatus() {
        return rentPaymentStatus;
    }

    public void setRentPaymentStatus(Boolean rentPaymentStatus) {
        this.rentPaymentStatus = rentPaymentStatus;
    }

    public Boolean getPenaltyActive() {
        return penaltyActive;
    }

    public void setPenaltyActive(Boolean penaltyActive) {
        this.penaltyActive = penaltyActive;
    }

    public Double getPenaltyPerDay() {
        return penaltyPerDay;
    }

    public void setPenaltyPerDay(Double penaltyPerDay) {
        this.penaltyPerDay = penaltyPerDay;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public String getPropertyNumberOrName() {
        return propertyNumberOrName;
    }

    public void setPropertyNumberOrName(String propertyNumberOrName) {
        this.propertyNumberOrName = propertyNumberOrName;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNationalIdOrPassport() {
        return nationalIdOrPassport;
    }

    public void setNationalIdOrPassport(String nationalIdOrPassport) {
        this.nationalIdOrPassport = nationalIdOrPassport;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getTenantAddedAt() {
        return tenantAddedAt;
    }

    public void setTenantAddedAt(LocalDateTime tenantAddedAt) {
        this.tenantAddedAt = tenantAddedAt;
    }

    public Boolean getTenantActive() {
        return tenantActive;
    }

    public void setTenantActive(Boolean tenantActive) {
        this.tenantActive = tenantActive;
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
                '}';
    }
}

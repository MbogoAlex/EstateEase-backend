package com.propertymanagement.PropertyManagement.dto;

public class RentPaymentDTO {
    private String transactionId;
    private int tenantId;
    private Double amountOfRentToBePaid;
    private Double amountOfRentPaid;

    public RentPaymentDTO() {}

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public Double getAmountOfRentPaid() {
        return amountOfRentPaid;
    }

    public void setAmountOfRentPaid(Double amountOfRentPaid) {
        this.amountOfRentPaid = amountOfRentPaid;
    }

    public Double getAmountOfRentToBePaid() {
        return amountOfRentToBePaid;
    }

    public void setAmountOfRentToBePaid(Double amountOfRentToBePaid) {
        this.amountOfRentToBePaid = amountOfRentToBePaid;
    }
}

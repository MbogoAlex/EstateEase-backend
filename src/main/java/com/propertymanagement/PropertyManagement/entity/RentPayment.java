package com.propertymanagement.PropertyManagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.Month;

@Entity
@Table(name = "rent_payment")
public class RentPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rent_payment_tbl_id")
    private int rentPaymentTblId;
    @Column(name = "transaction_id")
    private String transactionId;
    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @Column(name = "amount_of_rent_to_be_paid")
    private Double amountOfRentToBePaid;
    @Column(name = "amount_of_rent_paid")
    private Double amountOfRentPaid;
    @Column(name = "rent_payment_status")
    private Boolean rentPaymentStatus;
    @Column(name = "month")
    private Month month;
    @Column(name = "paid_at")
    private LocalDateTime paidAt;
    @Column(name = "unit_tenant_id")
    private int unitTenantId;

    public RentPayment() {}

    public RentPayment(int transactionId, Tenant tenant, Double amountOfRentPaid, Boolean rentPaymentStatus, Month month, LocalDateTime paidAt) {
        this.tenant = tenant;
        this.amountOfRentPaid = amountOfRentPaid;
        this.rentPaymentStatus = rentPaymentStatus;
        this.month = month;
        this.paidAt = paidAt;
    }

    public int getRentPaymentTblId() {
        return rentPaymentTblId;
    }

    public void setRentPaymentTblId(int rentPaymentTblId) {
        this.rentPaymentTblId = rentPaymentTblId;
    }



    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Double getAmountOfRentPaid() {
        return amountOfRentPaid;
    }

    public void setAmountOfRentPaid(Double amountOfRentPaid) {
        this.amountOfRentPaid = amountOfRentPaid;
    }

    public Boolean getRentPaymentStatus() {
        return rentPaymentStatus;
    }

    public void setRentPaymentStatus(Boolean rentPaymentStatus) {
        this.rentPaymentStatus = rentPaymentStatus;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public Double getAmountOfRentToBePaid() {
        return amountOfRentToBePaid;
    }

    public void setAmountOfRentToBePaid(Double amountOfRentToBePaid) {
        this.amountOfRentToBePaid = amountOfRentToBePaid;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public int getUnitTenantId() {
        return unitTenantId;
    }

    public void setUnitTenantId(int unitTenantId) {
        this.unitTenantId = unitTenantId;
    }

//    @Override
//    public String toString() {
//        return "RentPayment{" +
//                "rentPaymentTblId=" + rentPaymentTblId +
//                ", transactionId='" + transactionId + '\'' +
//                ", tenant=" + tenant +
//                ", amountOfRentToBePaid=" + amountOfRentToBePaid +
//                ", amountOfRentPaid=" + amountOfRentPaid +
//                ", rentPaymentStatus=" + rentPaymentStatus +
//                ", month=" + month +
//                ", paidAt=" + paidAt +
//                ", unitTenantId=" + unitTenantId +
//                '}';
//    }
}

package com.propertymanagement.PropertyManagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
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
@Entity
@Table(name = "rent_payment")
public class RentPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rent_payment_tbl_id")
    private int rentPaymentTblId;
    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "month")
    private Month month;
    @Column(name = "year")
    private Year year;
//    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @Column(name = "monthly_rent")
    private Double monthlyRent;
    @Column(name = "due_date")
    private LocalDate dueDate;
    @Column(name = "penalty_per_day")
    private Double penaltyPerDay;
    @Column(name = "penalty_active")
    private Boolean penaltyActive;
    @Column(name = "paid_amount")
    private Double paidAmount;

    @Column(name = "rent_payment_status")
    private Boolean paymentStatus;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;
    @Column(name = "paid_late")
    private Boolean paidLate;


    @Column(name = "unit_id")
    private int unitId;

}

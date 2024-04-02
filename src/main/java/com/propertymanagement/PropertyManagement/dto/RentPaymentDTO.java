package com.propertymanagement.PropertyManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentPaymentDTO {
//    private String transactionId

    private Boolean penaltyActive;
    private Double penaltyPerDay;
    private LocalDate dueDate;
//    private Double amountOfRentPaid;


}

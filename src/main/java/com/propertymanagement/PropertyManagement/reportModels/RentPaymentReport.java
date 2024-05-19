package com.propertymanagement.PropertyManagement.reportModels;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RentPaymentReport {
    private String year;
    private String month;
    private String monthlyRent;
    private String dueDate;
    private String penaltyStatus;
    private String penalty;
    private String paidAmount;
    private String paidAt;
}

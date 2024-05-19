package com.propertymanagement.PropertyManagement.reportModels;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeneralReport {
    private String tenantName;
    private String tenantPhoneNo;
    private String room;
    private String rooms;
    private String rent;
    private String dueDate;
    private String paymentStatus;
    private String penalty;
    private String payableAmount;
    private String paidAt;
}

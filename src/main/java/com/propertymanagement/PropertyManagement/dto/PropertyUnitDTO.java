package com.propertymanagement.PropertyManagement.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PropertyUnitDTO {
    private String rooms;
    private String propertyNumberOrName;
    private String propertyDescription;
    private Double monthlyRent;

    private Boolean latePaymentDailyPenalty;

    private int propertyManagerId;


}

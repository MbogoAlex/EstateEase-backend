package com.propertymanagement.PropertyManagement.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PropertyUnitDTO {
    private int numberOfRooms;
    private String propertyNumberOrName;
    private String propertyDescription;
    private Double monthlyRent;

    private Double latePaymentDailyPenalty;

    private int propertyManagerId;


}

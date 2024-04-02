package com.propertymanagement.PropertyManagement.dto.propertyResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyUnitResponseDTO {
    private int propertyUnitId;
    private int numberOfRooms;
    private String propertyNumberOrName;
    private String propertyDescription;
    private Double monthlyRent;
    private String propertyAddedAt;
    private Boolean propertyAssignmentStatus;
    private List<PropertyTenantDTO> tenants = new ArrayList<>();
}

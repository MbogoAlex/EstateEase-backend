package com.propertymanagement.PropertyManagement.dto;

public class PropertyUnitDTO {
    private int numberOfRooms;
    private String propertyNumberOrName;
    private String propertyDescription;
    private Double monthlyRent;

    private int propertyManagerId;

    public PropertyUnitDTO() {}
    public PropertyUnitDTO(int numberOfRooms, String propertyNumberOrName, String propertyDescription, Double monthlyRent, int propertyManagerId) {
        this.numberOfRooms = numberOfRooms;
        this.propertyNumberOrName = propertyNumberOrName;
        this.propertyDescription = propertyDescription;
        this.monthlyRent = monthlyRent;
        this.propertyManagerId = propertyManagerId;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public String getPropertyNumberOrName() {
        return propertyNumberOrName;
    }

    public void setPropertyNumberOrName(String propertyNumberOrName) {
        this.propertyNumberOrName = propertyNumberOrName;
    }

    public String getPropertyDescription() {
        return propertyDescription;
    }

    public void setPropertyDescription(String propertyDescription) {
        this.propertyDescription = propertyDescription;
    }

    public Double getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(Double monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public int getPropertyManagerId() {
        return propertyManagerId;
    }

    public void setPropertyManagerId(int propertyManagerId) {
        this.propertyManagerId = propertyManagerId;
    }
}

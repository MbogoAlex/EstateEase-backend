package com.propertymanagement.PropertyManagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "property_unit")
public class PropertyUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_unit_id")
    private int propertyUnitId;
    @Column(name = "number_of_rooms")
    private int numberOfRooms;
    @Column(name = "property_number_or_name", unique = true)
    private String propertyNumberOrName;
    @Column(name = "property_description")
    private String propertyDescription;
    @Column(name = "monthly_rent")
    private Double monthlyRent;
    @Column(name = "property_added_at")
    private LocalDateTime propertyAddedAt;
    @Column(name = "property_assignment_status")
    private Boolean propertyAssignmentStatus;
//    @JsonBackReference
    @OneToMany(mappedBy = "propertyUnit", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
//    @JoinColumn(name = "tenant_id")
    private List<Tenant> tenants = new ArrayList<>();

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "pmanager_id")
    private PManager pManager;


    public PropertyUnit() {}

    public PropertyUnit(int numberOfRooms, String propertyNumberOrName, String propertyDescription, Double monthlyRent) {
        this.numberOfRooms = numberOfRooms;
        this.propertyNumberOrName = propertyNumberOrName;
        this.propertyDescription = propertyDescription;
        this.monthlyRent = monthlyRent;
    }

    public int getPropertyUnitId() {
        return propertyUnitId;
    }

    public void setPropertyUnitId(int propertyUnitId) {
        this.propertyUnitId = propertyUnitId;
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

    public LocalDateTime getPropertyAddedAt() {
        return propertyAddedAt;
    }

    public void setPropertyAddedAt(LocalDateTime propertyAddedAt) {
        this.propertyAddedAt = propertyAddedAt;
    }



    public Boolean getPropertyAssignmentStatus() {
        return propertyAssignmentStatus;
    }

    public void setPropertyAssignmentStatus(Boolean propertyAssignmentStatus) {
        this.propertyAssignmentStatus = propertyAssignmentStatus;
    }

    public List<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(List<Tenant> tenants) {
        this.tenants = tenants;
    }


    public PManager getpManager() {
        return pManager;
    }

    public void setpManager(PManager pManager) {
        this.pManager = pManager;
    }

    @Override
    public String toString() {
        return "PropertyUnit{" +
                "propertyUnitId=" + propertyUnitId +
                ", numberOfRooms=" + numberOfRooms +
                ", propertyNumberOrName='" + propertyNumberOrName + '\'' +
                ", propertyDescription='" + propertyDescription + '\'' +
                ", monthlyRent=" + monthlyRent +
                ", propertyAddedAt=" + propertyAddedAt +
                ", propertyAssignmentStatus=" + propertyAssignmentStatus +
                '}';
    }
}

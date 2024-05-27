package com.propertymanagement.PropertyManagement.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "property_manager")
public class PManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pManager_id")
    private int pManagerId;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "national_id_or_passport_number")
    private String nationalIdOrPassportNumber;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    private Boolean pManagerActiveStatus;
    @Column(name = "property_manager_added_at")
    private LocalDateTime propertyManagerAddedAt;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "pmanager_role",
            joinColumns = @JoinColumn(name = "pManager_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();
    @JsonManagedReference
    @OneToMany(mappedBy = "pManager", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<PropertyUnit> propertyUnits = new ArrayList<>();
    @JsonManagedReference
    @OneToMany(mappedBy = "pManager", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Tenant> tenants = new ArrayList<>();

    @OneToMany(mappedBy = "pManager", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Caretaker> caretakers = new ArrayList<>();

    public PManager() {}

    public PManager(String fullName, String nationalIdOrPassportNumber, String phoneNumber, String email) {
        this.fullName = fullName;
        this.nationalIdOrPassportNumber = nationalIdOrPassportNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNationalIdOrPassportNumber() {
        return nationalIdOrPassportNumber;
    }

    public void setNationalIdOrPassportNumber(String nationalIdOrPassportNumber) {
        this.nationalIdOrPassportNumber = nationalIdOrPassportNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getPropertyManagerAddedAt() {
        return propertyManagerAddedAt;
    }

    public void setPropertyManagerAddedAt(LocalDateTime propertyManagerAddedAt) {
        this.propertyManagerAddedAt = propertyManagerAddedAt;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<PropertyUnit> getPropertyUnits() {
        return propertyUnits;
    }

    public void setPropertyUnits(List<PropertyUnit> propertyUnits) {
        this.propertyUnits = propertyUnits;
    }

    public List<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(List<Tenant> tenants) {
        this.tenants = tenants;
    }

    public int getpManagerId() {
        return pManagerId;
    }

    public void setpManagerId(int pManagerId) {
        this.pManagerId = pManagerId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getpManagerActiveStatus() {
        return pManagerActiveStatus;
    }

    public void setpManagerActiveStatus(Boolean pManagerActiveStatus) {
        this.pManagerActiveStatus = pManagerActiveStatus;
    }

    @Override
    public String toString() {
        return "PManager{" +
                "pManagerId=" + pManagerId +
                ", fullName='" + fullName + '\'' +
                ", nationalIdOrPassportNumber='" + nationalIdOrPassportNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", pManagerActiveStatus=" + pManagerActiveStatus +
                ", propertyManagerAddedAt=" + propertyManagerAddedAt +
                ", roles=" + roles +
                ", propertyUnits=" + propertyUnits +
                ", tenants=" + tenants +
                '}';
    }
}

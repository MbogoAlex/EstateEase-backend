package com.propertymanagement.PropertyManagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tenant")
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tenant_id")
    private int tenantId;
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
    @Column(name = "tenant_added_at")
    private LocalDateTime tenantAddedAt;

    private Boolean tenantActive;

    @Column(name = "tenant_archived_at")
    private LocalDateTime tenantArchivedAt;
    @JsonManagedReference
    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<RentPayment> rentPayments = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "tenant_role",
            joinColumns = @JoinColumn(name = "tenant_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();
//    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "property_unit_ref_id")
    private PropertyUnit propertyUnit;
    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "pmanager_id")
    private PManager pManager;

//    @Column(name = "property_unit_id")
//    private int propertyUnitId;


    public Tenant() {}
    public Tenant(String fullName, String nationalIdOrPassportNumber, String phoneNumber, String email, LocalDateTime tenantAddedAt, List<Role> roles) {
        this.fullName = fullName;
        this.nationalIdOrPassportNumber = nationalIdOrPassportNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.tenantAddedAt = tenantAddedAt;
        this.roles = roles;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public List<RentPayment> getRentPayments() {
        return rentPayments;
    }

    public void setRentPayments(List<RentPayment> rentPayments) {
        this.rentPayments = rentPayments;
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

    public LocalDateTime getTenantAddedAt() {
        return tenantAddedAt;
    }

    public void setTenantAddedAt(LocalDateTime tenantAddedAt) {
        this.tenantAddedAt = tenantAddedAt;
    }

    public Boolean getTenantActive() {
        return tenantActive;
    }

    public void setTenantActive(Boolean tenantActive) {
        this.tenantActive = tenantActive;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PropertyUnit getPropertyUnit() {
        return propertyUnit;
    }

    public void setPropertyUnit(PropertyUnit propertyUnit) {
        this.propertyUnit = propertyUnit;
    }

    public PManager getpManager() {
        return pManager;
    }

    public void setpManager(PManager pManager) {
        this.pManager = pManager;
    }

    public LocalDateTime getTenantArchivedAt() {
        return tenantArchivedAt;
    }

    public void setTenantArchivedAt(LocalDateTime tenantArchivedAt) {
        this.tenantArchivedAt = tenantArchivedAt;
    }

    //    public int getPropertyUnitId() {
//        return propertyUnitId;
//    }
//
//    public void setPropertyUnitId(int propertyUnitId) {
//        this.propertyUnitId = propertyUnitId;
//    }

    //    @Override
//    public String toString() {
//        return "Tenant{" +
//                "tenantId=" + tenantId +
//                ", fullName='" + fullName + '\'' +
//                ", nationalIdOrPassportNumber='" + nationalIdOrPassportNumber + '\'' +
//                ", phoneNumber='" + phoneNumber + '\'' +
//                ", email='" + email + '\'' +
//                ", password='" + password + '\'' +
//                ", tenantAddedAt=" + tenantAddedAt +
//                ", tenantActive=" + tenantActive +
//                ", rentPayments=" + rentPayments +
//                ", roles=" + roles +
//                ", propertyUnit=" + propertyUnit +
//                ", pManager=" + pManager +
//                '}';
//    }
}

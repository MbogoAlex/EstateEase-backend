package com.propertymanagement.PropertyManagement.dto;

import com.propertymanagement.PropertyManagement.entity.Role;

import java.time.LocalDateTime;

public class TenantDTO {
    private String fullName;
    private String nationalIdOrPassportNumber;
    private String phoneNumber;
    private String email;

    private String password;

    private int roleId;

    private int propertyUnitId;
    private int tenantAddedByPManagerId;

    public TenantDTO() {}
    public TenantDTO(String fullName, String nationalIdOrPassportNumber, String phoneNumber, String email, String password, String tenantAddedAt) {
        this.fullName = fullName;
        this.nationalIdOrPassportNumber = nationalIdOrPassportNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getPropertyUnitId() {
        return propertyUnitId;
    }

    public void setPropertyUnitId(int propertyUnitId) {
        this.propertyUnitId = propertyUnitId;
    }

    public int getTenantAddedByPManagerId() {
        return tenantAddedByPManagerId;
    }

    public void setTenantAddedByPManagerId(int tenantAddedByPManagerId) {
        this.tenantAddedByPManagerId = tenantAddedByPManagerId;
    }
}


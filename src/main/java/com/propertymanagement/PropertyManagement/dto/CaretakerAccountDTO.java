package com.propertymanagement.PropertyManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CaretakerAccountDTO {
    private String fullName;
    private String nationalIdOrPassportNumber;
    private String phoneNumber;
    private String email;
    private String password;
    private int roleId;
    private int pManagerId;
    private Double salary;

    public CaretakerAccountDTO(String fullName, String nationalIdOrPassportNumber, String phoneNumber, String email, String password, int roleId, int pManagerId, Double salary) {
        this.fullName = fullName;
        this.nationalIdOrPassportNumber = nationalIdOrPassportNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
        this.pManagerId = pManagerId;
        this.salary = salary;
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

    public int getpManagerId() {
        return pManagerId;
    }

    public void setpManagerId(int pManagerId) {
        this.pManagerId = pManagerId;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "CaretakerAccountDTO{" +
                "fullName='" + fullName + '\'' +
                ", nationalIdOrPassportNumber='" + nationalIdOrPassportNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roleId=" + roleId +
                ", pManagerId=" + pManagerId +
                ", salary=" + salary +
                '}';
    }
}

package com.propertymanagement.PropertyManagement.dto;


public class PManagerDTO {
    private String fullName;
    private String nationalIdOrPassportNumber;
    private String phoneNumber;
    private String email;

    private String password;
    private int roleId;


    public PManagerDTO() {}
    public PManagerDTO(String fullName, String nationalIdOrPassportNumber, String phoneNumber, String email, String password, int roleId) {
        this.fullName = fullName;
        this.nationalIdOrPassportNumber = nationalIdOrPassportNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "PManagerDTO{" +
                "fullName='" + fullName + '\'' +
                ", nationalIdOrPassportNumber='" + nationalIdOrPassportNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}

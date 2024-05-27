package com.propertymanagement.PropertyManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "caretaker")
public class Caretaker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "caretaker_id")
    private int caretakerId;
    @Column(name = "national_id_or_passport_number")
    private String nationalIdOrPassportNumber;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "password")
    private String password;
    @Column(name = "salary")
    private Double salary;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "caretaker_added_at")
    private LocalDateTime caretakerAddedAt;
    @ManyToMany
    @JoinTable(
            name = "caretaker_role",
            joinColumns = @JoinColumn(name = "caretaker_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "caretaker_ref_id")
    private PManager pManager;
}

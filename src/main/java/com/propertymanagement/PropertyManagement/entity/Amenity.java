package com.propertymanagement.PropertyManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "amenity")
public class Amenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "amenity_name")
    private String amenityName;
    @Column(name = "provider_name")
    private String providerName;
    @Column(name = "provider_phonenumber")
    private String providerPhoneNumber;
    @Column(name = "provider_email")
    private String providerEmail;
    @Column(name = "amenity_description")
    private String amenityDescription;
    @Column(name = "added_at")
    private LocalDateTime addedAt;
    @Column(name = "added_by")
    private String addedBy;
    @Column(name = "pmanager_id")
    private int pManagerId;
    @Column(name = "images")
    @OneToMany(mappedBy = "amenity", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<AmenityImage> images = new ArrayList<>();
}

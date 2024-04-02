package com.propertymanagement.PropertyManagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @Column(name = "late_payment_daily_penalty")
    private Double latePaymentDailyPenalty;
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

}

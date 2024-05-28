package com.propertymanagement.PropertyManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "water_meter_data")
public class WaterMeterData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "water_meter_data_id")
    private int id;
    @Column(name = "water_units")
    private Double waterUnits;
    @Column(name = "price_per_unit")
    private Double pricePerUnit;
    @Column(name = "meter_reading_date")
    private LocalDateTime meterReadingDate;
    @Column(name = "month")
    private String month;
    @Column(name = "year")
    private String year;
    @Column(name = "meter_reading_taken")
    private Boolean meterReadingTaken;
    @Column(name = "paid")
    private Boolean paid;
    @OneToOne(mappedBy = "waterMeterData", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private WaterMeterImage waterMeterImage;

    @OneToOne(mappedBy = "waterMeterData", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,  CascadeType.REFRESH})
    private RentPayment rentPayment;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "water_meter_ref_id")
    private PropertyUnit propertyUnit;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "water_meter_dt_ref_id")
    private Tenant tenant;
}

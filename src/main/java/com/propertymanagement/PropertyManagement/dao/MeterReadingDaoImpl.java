package com.propertymanagement.PropertyManagement.dao;

import com.propertymanagement.PropertyManagement.entity.WaterMeterData;
import com.propertymanagement.PropertyManagement.entity.WaterMeterImage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Repository
public class MeterReadingDaoImpl implements MeterReadingDao{
    private EntityManager entityManager;
    @Autowired
    public MeterReadingDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public WaterMeterData addMeterWaterReading(WaterMeterData waterMeterData) {
        entityManager.persist(waterMeterData);
        return waterMeterData;
    }

    @Override
    public WaterMeterData getMeterWaterReadingById(int id) {
        TypedQuery<WaterMeterData> query = entityManager.createQuery("from WaterMeterData where id = :id", WaterMeterData.class);
        return query.getSingleResult();
    }

    @Override
    public List<WaterMeterData> getMeterWaterReadings(String month, String year, Boolean meterReadingTaken) {
        TypedQuery<WaterMeterData> query = entityManager.createQuery("from WaterMeterData where month = :month and year = :year and meterReadingTaken = :meterReadingTaken", WaterMeterData.class);
        query.setParameter("month", month);
        query.setParameter("year", year);
        query.setParameter("meterReadingTaken", meterReadingTaken);
        return query.getResultList();
    }

    @Override
    public String deleteImage(int id) {
        Query query = entityManager.createQuery("delete from WaterMeterImage where id = :id");
        query.setParameter("id", id);
        int deletedCount = query.executeUpdate();
        return "Deleted " + deletedCount + "images";
    }

    @Override
    public WaterMeterData updateMeterReading(WaterMeterData waterMeterData) {
        entityManager.merge(waterMeterData);
        return waterMeterData;
    }

    @Override
    public WaterMeterImage getImageById(int id) {
        TypedQuery<WaterMeterImage> query = entityManager.createQuery("from WaterMeterImage where id = :id", WaterMeterImage.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
}

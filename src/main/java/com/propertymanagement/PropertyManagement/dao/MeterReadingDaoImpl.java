package com.propertymanagement.PropertyManagement.dao;

import com.propertymanagement.PropertyManagement.entity.WaterMeterData;
import com.propertymanagement.PropertyManagement.entity.WaterMeterImage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public List<WaterMeterData> getMeterWaterReadings(String month, String year, Boolean meterReadingTaken, String tenantName, String propertyName) {
        System.out.println("month: "+month+" year: "+year+" meterReadingTaken: "+meterReadingTaken+" tenantName: "+tenantName+" propertyName: "+propertyName);
        TypedQuery<WaterMeterData> query = entityManager.createQuery("from WaterMeterData " +
                "where (:month is null or :month = '' or month = :month) " +
                "and (:year is null or :year = '' or year = :year) " +
                "and (:meterReadingTaken is null or meterReadingTaken = :meterReadingTaken) " +
                "and (:tenantName is null or tenant.fullName like concat('%', :tenantName, '%')) " +
                "and (:propertyName is null or propertyUnit.propertyNumberOrName like concat('%', :propertyName, '%'))", WaterMeterData.class);

        query.setParameter("month", month);
        query.setParameter("year", year);
        query.setParameter("meterReadingTaken", meterReadingTaken);
        query.setParameter("tenantName", tenantName);
        query.setParameter("propertyName", propertyName);
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

    @Override
    public WaterMeterData getWaterMeterDataById(int id) {
        TypedQuery<WaterMeterData> query = entityManager.createQuery("from WaterMeterData where id = :id", WaterMeterData.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
}

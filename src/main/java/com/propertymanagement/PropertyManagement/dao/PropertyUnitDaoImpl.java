package com.propertymanagement.PropertyManagement.dao;

import com.propertymanagement.PropertyManagement.entity.PropertyUnit;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class PropertyUnitDaoImpl implements PropertyUnitDao{
    private EntityManager entityManager;

    public PropertyUnitDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public PropertyUnit addNewProperty(PropertyUnit propertyUnit) {
        entityManager.persist(propertyUnit);
        return propertyUnit;
    }

    @Override
    public PropertyUnit updateProperty(PropertyUnit propertyUnit) {
        entityManager.merge(propertyUnit);
        return null;
    }

    @Override
    public String deleteProperty(int id) {
        Query query = entityManager.createQuery("delete from PropertyUnit where propertyUnitId = :data");
        query.setParameter("data", id);
        int deleteCount = query.executeUpdate();
        return "Deleted "+deleteCount+" unit(s)";
    }

    @Override
    public List<PropertyUnit> getAllProperties() {
        TypedQuery<PropertyUnit> query = entityManager.createQuery("from PropertyUnit", PropertyUnit.class);
        return query.getResultList();
    }

    @Override
    public PropertyUnit getPropertyByPropertyId(int propertyId) {
        TypedQuery<PropertyUnit> query = entityManager.createQuery("from PropertyUnit where propertyUnitId = :data", PropertyUnit.class);
        query.setParameter("data", propertyId);
        return query.getSingleResult();
    }

    @Override
    public PropertyUnit archiveProperty(PropertyUnit propertyUnit) {
        entityManager.merge(propertyUnit);
        return propertyUnit;
    }

    @Override
    public PropertyUnit fetchPropertyByNumberOrId(String propertyNumOrName) {
        TypedQuery<PropertyUnit> query = entityManager.createQuery("from PropertyUnit where propertyNumberOrName = :data", PropertyUnit.class);
        query.setParameter("data", propertyNumOrName);
        return query.getSingleResult();
    }

    @Override
    public List<PropertyUnit> fetchAllOccupiedUnits() {
        TypedQuery<PropertyUnit> query = entityManager.createQuery("from PropertyUnit where propertyAssignmentStatus = true", PropertyUnit.class);
        return query.getResultList();
    }
}

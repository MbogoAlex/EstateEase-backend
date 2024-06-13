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
        return propertyUnit;
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
        System.out.println("FIND NY PROPERTY ID: "+propertyId);
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

    @Override
    public List<PropertyUnit> fetchAllUnoccupiedUnits() {
        TypedQuery<PropertyUnit> query = entityManager.createQuery("from PropertyUnit where propertyAssignmentStatus = false", PropertyUnit.class);
        return query.getResultList();
    }

    @Override
    public List<PropertyUnit> fetchFilteredUnits(String name, String rooms, Boolean assignmentStatus) {
        String queryString = "from PropertyUnit where (:propertyNumberOrName is null or propertyNumberOrName = :propertyNumberOrName or propertyNumberOrName like concat('%', :propertyNumberOrName, '%')) and (:numberOfRooms is null or rooms = :numberOfRooms) and (propertyAssignmentStatus = :propertyAssignmentStatus)";
        TypedQuery<PropertyUnit> query = entityManager.createQuery(queryString, PropertyUnit.class);
        query.setParameter("propertyNumberOrName", name);
        query.setParameter("numberOfRooms", rooms);
        query.setParameter("propertyAssignmentStatus", assignmentStatus);

        return query.getResultList();
    }



    @Override
    public List<PropertyUnit> fetchUnitsFilteredByRoomName(String name, Boolean assignmentStatus) {
        TypedQuery<PropertyUnit> query = entityManager.createQuery("from PropertyUnit where propertyNumberOrName = :propertyNumberOrName and propertyAssignmentStatus =:propertyAssignmentStatus", PropertyUnit.class);
        query.setParameter("propertyNumberOrName", name);
        query.setParameter("propertyAssignmentStatus", assignmentStatus);
        return query.getResultList();
    }

    @Override
    public List<PropertyUnit> fetchUnitsFilteredByRooms(int rooms, Boolean assignmentStatus) {
        TypedQuery<PropertyUnit> query = entityManager.createQuery("from PropertyUnit where numberOfRooms = :numberOfRooms and propertyAssignmentStatus = :propertyAssignmentStatus", PropertyUnit.class);
        query.setParameter("numberOfRooms", rooms);
        query.setParameter("propertyAssignmentStatus", assignmentStatus);
        return query.getResultList();
    }
}

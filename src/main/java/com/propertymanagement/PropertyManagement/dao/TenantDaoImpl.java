package com.propertymanagement.PropertyManagement.dao;

import com.propertymanagement.PropertyManagement.entity.RentPayment;
import com.propertymanagement.PropertyManagement.entity.Tenant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TenantDaoImpl implements TenantDao{
    private EntityManager entityManager;
    @Autowired
    public TenantDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public Tenant addNewTenant(Tenant tenant) {
        entityManager.persist(tenant);
        return tenant;
    }

    @Override
    public Tenant updateTenant(Tenant tenant) {
        entityManager.merge(tenant);
        return tenant;
    }

    @Override
    public RentPayment addNewRentPaymentRow(RentPayment rentPayment) {
        entityManager.persist(rentPayment);
        return null;
    }

    @Override
    public RentPayment payRent(RentPayment rentPayment) {
        entityManager.merge(rentPayment);
        return rentPayment;
    }

    @Override
    public Tenant archiveTenant(Tenant tenant) {
        entityManager.merge(tenant);
        return tenant;
    }

    @Override
    public List<Tenant> getAllTenants() {
        TypedQuery<Tenant> query = entityManager.createQuery("from Tenant", Tenant.class);
        return query.getResultList();
    }

    @Override
    public Tenant getTenantByTenantId(int tenantId) {
        TypedQuery<Tenant> query = entityManager.createQuery("from Tenant where tenantId = :data", Tenant.class);
        query.setParameter("data", tenantId);
        return query.getSingleResult();
    }

    @Override
    public RentPayment getRentPaymentRow(int rentPaymentTblId) {
        TypedQuery<RentPayment> query = entityManager.createQuery("from RentPayment where rentPaymentTblId = :data", RentPayment.class);
        query.setParameter("data", rentPaymentTblId);
        return query.getSingleResult();
    }

    @Override
    public Tenant fetchTenantByPasswordAndPhoneNumber(String password, String phoneNumber) {
        TypedQuery<Tenant> query = entityManager.createQuery("from Tenant where password = :password and phoneNumber = :phoneNumber and tenantActive = true", Tenant.class);
        query.setParameter("password", password);
        query.setParameter("phoneNumber", phoneNumber);
        return query.getSingleResult();
    }

    @Override
    public List<Tenant> getActiveTenants() {
        TypedQuery<Tenant> query = entityManager.createQuery("from Tenant where tenantActive = true", Tenant.class);
        return query.getResultList();
    }
}

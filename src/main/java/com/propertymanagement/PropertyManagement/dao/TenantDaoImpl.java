package com.propertymanagement.PropertyManagement.dao;

import com.propertymanagement.PropertyManagement.dto.DetailedRentPaymentInfoDTO;
import com.propertymanagement.PropertyManagement.dto.RentPaymentDetailsDTO;
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
    public Tenant fetchTenantByPhoneNumberAndPassword(String phoneNumber, String password) {
        TypedQuery<Tenant> query = entityManager.createQuery("from Tenant where phoneNumber = :phoneNumber and password = :password", Tenant.class);
        query.setParameter("phoneNumber", phoneNumber);
        query.setParameter("password", password);
        return query.getSingleResult();
    }


    @Override
    public List<Tenant> getActiveTenants() {
        TypedQuery<Tenant> query = entityManager.createQuery("from Tenant where tenantActive = true", Tenant.class);
        return query.getResultList();
    }

    @Override
    public List<RentPayment> getRentPaymentRows(String month, String year) {
        TypedQuery<RentPayment> query = entityManager.createQuery("from RentPayment where MONTHNAME(dueDate) = :month AND YEAR(dueDate) = :year", RentPayment.class);
        query.setParameter("month", month);
        query.setParameter("year", year);
        return query.getResultList();
    }

    @Override
    public RentPayment getSingleRentPaymentRow(int rentPaymentTblId) {
        TypedQuery<RentPayment> query = entityManager.createQuery("from RentPayment where rentPaymentTblId = :rentPaymentTblId", RentPayment.class);
        query.setParameter("rentPaymentTblId", rentPaymentTblId);
        return query.getSingleResult();
    }

    @Override
    public RentPayment getSingleRentPaymentRowByTransactionId(String transactionId) {
        TypedQuery<RentPayment> query = entityManager.createQuery("from RentPayment where transactionId = :transactionId", RentPayment.class);
        query.setParameter("transactionId", transactionId);
        return query.getSingleResult();
    }

    @Override
    public RentPayment updateRentPaymentRow(RentPayment rentPayment) {
        entityManager.merge(rentPayment);
        return rentPayment;
    }

    @Override
    public List<DetailedRentPaymentInfoDTO> getRentPaymentRowsByTenantId(Integer tenantId, String month, Integer year, String roomName, String rooms, String tenantName, Boolean rentPaymentStatus, Boolean paidLate, Boolean tenantActive) {

        System.out.println("MONTH: "+month+" YEAR: "+year+" ROOMNAME: "+roomName+" ROOMS: "+rooms+" TENANTNAME: "+tenantName+" TENANTID: "+tenantId+" RENTPAYMENTSTATUS: "+rentPaymentStatus+" PAIDLATE: "+paidLate+" TENANTACTIVE: "+tenantActive);

        String stringQuery = "select new DetailedRentPaymentInfoDTO(" +
                "rp.rentPaymentTblId, " +
                "rp.dueDate, " +
                "rp.month, " +
                "rp.monthlyRent, " +
                "rp.paidAmount, " +
                "rp.paidAt, " +
                "rp.paidLate, " +
                "rp.paymentStatus, " +
                "rp.penaltyActive, " +
                "rp.penaltyPerDay, " +
                "rp.transactionId, " +
                "rp.year, " +
                "pu.propertyNumberOrName, " +
                "pu.rooms, " +
                "t.tenantId, " +
                "t.email, " +
                "t.fullName, " +
                "t.nationalIdOrPassportNumber, " +
                "t.phoneNumber, " +
                "t.tenantAddedAt, " +
                "t.tenantActive, " +
                "wmd.waterUnits, " +
                "wmd.pricePerUnit, " +
                "wmd.meterReadingDate, " +
                "wmi.name) " +
                "from RentPayment rp " +
                "join rp.tenant t " +
                "join t.propertyUnit pu " +
                "left join WaterMeterData wmd on :previousMonth = wmd.month and :waterDataYear = wmd.year " +
                "left join wmd.waterMeterImage wmi " +
                "where t.tenantId = :tenantId " +
                "and (:month is null or :month = '' or MONTHNAME(rp.dueDate) = :month)" +
                "and (:year is null or YEAR(rp.dueDate) = :year)" +
                "and (:tenantName is null or t.fullName like concat('%', :tenantName, '%')) " +
                "and (:numberOfRooms is null or pu.rooms like concat('%', :numberOfRooms, '%')) " +
                "and (:propertyNumberOrName is null or pu.propertyNumberOrName like concat('%', :propertyNumberOrName, '%')) " +
                "and (:rentPaymentStatus is null or rp.paymentStatus = :rentPaymentStatus)" +
                "and (:paidLate is null or rp.paidLate = :paidLate)" +
                "and (:tenantActive is null or t.tenantActive = :tenantActive)";

        TypedQuery<DetailedRentPaymentInfoDTO> query = entityManager.createQuery(stringQuery, DetailedRentPaymentInfoDTO.class);
        query.setParameter("month", month);
        query.setParameter("year", year);
        query.setParameter("previousMonth", month);
        query.setParameter("waterDataYear", month);
        query.setParameter("tenantName", tenantName);
        query.setParameter("tenantId", tenantId);
        query.setParameter("numberOfRooms", rooms);
        query.setParameter("propertyNumberOrName", roomName);
        query.setParameter("rentPaymentStatus", rentPaymentStatus);
        query.setParameter("paidLate", paidLate);
        query.setParameter("tenantActive", tenantActive);

        return query.getResultList();
    }


}

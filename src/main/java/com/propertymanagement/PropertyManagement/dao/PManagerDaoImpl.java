package com.propertymanagement.PropertyManagement.dao;

import com.propertymanagement.PropertyManagement.dto.DetailedRentPaymentInfoDTO;
import com.propertymanagement.PropertyManagement.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class PManagerDaoImpl implements PManagerDao {

    private EntityManager entityManager;
    @Autowired
    public PManagerDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public PManager addNewPManager(PManager pManager) {
        entityManager.persist(pManager);
        return pManager;
    }

    @Override
    public PManager updatePmanager(PManager pManager) {
        entityManager.merge(pManager);
        return pManager;
    }

    @Override
    public PManager archivePManager(PManager pManager) {
        entityManager.merge(pManager);
        return pManager;
    }

    @Override
    public PropertyUnit addNewUnit(PropertyUnit propertyUnit) {
        entityManager.persist(propertyUnit);
        return propertyUnit;
    }

    @Override
    public Tenant addNewTenant(Tenant tenant) {
        entityManager.persist(tenant);
        return tenant;
    }

    @Override
    public List<PropertyUnit> getAllPropertyUnits() {
        TypedQuery<PropertyUnit> query = entityManager.createQuery("from PropertyUnit", PropertyUnit.class);
        return query.getResultList();
    }

    @Override
    public List<Tenant> getAllTenants() {
        TypedQuery<Tenant> query = entityManager.createQuery("from Tenant", Tenant.class);
        return query.getResultList();
    }

    @Override
    public PManager getPManagerById(int id) {
        TypedQuery<PManager> query = entityManager.createQuery("from PManager where pManagerId = :data", PManager.class);
        query.setParameter("data", id);
        System.out.println("PMANAGER OF ID: "+id+ " RETRIEVED:");
//        System.out.println(query.getResultList().toString());
        return query.getSingleResult();

    }

    @Override
    public Tenant addNewTenant(Tenant tenant, int pManagerId, int roomNameOrNumber) {
        entityManager.persist(tenant);
        return tenant;
    }

    @Override
    public PropertyUnit getPropertyByRoomNumOrName(String roomNumOrName) {
        TypedQuery<PropertyUnit> query = entityManager.createQuery("from PropertyUnit where propertyNumberOrName = :data", PropertyUnit.class);
        query.setParameter("data", roomNumOrName);
        return query.getSingleResult();
    }

    @Override
    public Role getRoleById(int id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public Role addNewRole(Role role) {
        entityManager.persist(role);
        return role;
    }

    @Override
    public String deletePropertyManager(int id) {
        PManager pManager = entityManager.find(PManager.class, id);
        entityManager.remove(pManager);
        return "Property manager removed successfully";
    }

    @Override
    public String deleteRole(int id) {
        Query query = entityManager.createQuery("delete from Role where roleId = :data");
        query.setParameter("data", id);
        int deletedCount = query.executeUpdate();
        return "Deleted " +deletedCount +" Role(s) successfully";
    }

    @Override
    public PManager findPManagerByPasswordAndEmail(String email, String password) {
        TypedQuery<PManager> query = entityManager.createQuery("from PManager where email =:email and password = :password and pManagerActiveStatus = true", PManager.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        return query.getSingleResult();
    }

    @Override
    public List<RentPayment> getRentPaymentOverview(String month, String year) {
        TypedQuery<RentPayment> query = entityManager.createQuery("from RentPayment where MONTHNAME(dueDate) = :month AND YEAR(dueDate) = :year", RentPayment.class);
        query.setParameter("month", month);
        query.setParameter("year", year);
        return query.getResultList();
    }

    @Override
    public List<DetailedRentPaymentInfoDTO> getDetailedRentPayments(String month, String year, String propertyNumberOrName, Integer numberOfRooms, String tenantName, Integer tenantId, Boolean rentPaymentStatus, Boolean paidLate, Boolean tenantActive) {
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
                "pu.numberOfRooms, " +
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
                "wmi.name)" +
                "from RentPayment rp " +
                "join rp.tenant t " +
                "join t.propertyUnit pu " +
                "left join rp.waterMeterData wmd " +
                "left join wmd.waterMeterImage wmi " +
                "where MONTHNAME(rp.dueDate) = :month " +
                "and YEAR(rp.dueDate) = :year " +
                "and (:tenantName is null or t.fullName like concat('%', :tenantName, '%')) " +
                "and (:tenantId is null or t.tenantId = :tenantId) " +
                "and (:numberOfRooms is null or pu.numberOfRooms = :numberOfRooms) " +
                "and (:propertyNumberOrName is null or pu.propertyNumberOrName like concat('%', :propertyNumberOrName, '%')) " +
                "and (:rentPaymentStatus is null or rp.paymentStatus = :rentPaymentStatus) " +
                "and (:paidLate is null or rp.paidLate = :paidLate) " +
                "and (:tenantActive is null or t.tenantActive = :tenantActive)";

        TypedQuery<DetailedRentPaymentInfoDTO> query = entityManager.createQuery(stringQuery, DetailedRentPaymentInfoDTO.class);
        query.setParameter("month", month);
        query.setParameter("year", year);
        query.setParameter("tenantName", tenantName);
        query.setParameter("tenantId", tenantId);
        query.setParameter("numberOfRooms", numberOfRooms);
        query.setParameter("propertyNumberOrName", propertyNumberOrName);
        query.setParameter("rentPaymentStatus", rentPaymentStatus);
        query.setParameter("paidLate", paidLate);
        query.setParameter("tenantActive", tenantActive);

        return query.getResultList();
    }

}

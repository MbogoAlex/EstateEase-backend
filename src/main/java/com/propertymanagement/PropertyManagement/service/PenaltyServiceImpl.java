package com.propertymanagement.PropertyManagement.service;

import com.propertymanagement.PropertyManagement.dao.PenaltyDao;
import com.propertymanagement.PropertyManagement.dao.TenantDao;
import com.propertymanagement.PropertyManagement.entity.Penalty;
import com.propertymanagement.PropertyManagement.entity.RentPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PenaltyServiceImpl implements PenaltyService{
    private final PenaltyDao penaltyDao;
    private final TenantDao tenantDao;
    @Autowired
    public PenaltyServiceImpl(
            PenaltyDao penaltyDao,
            TenantDao tenantDao
    ) {
        this.penaltyDao = penaltyDao;
        this.tenantDao = tenantDao;
    }
    @Transactional
    @Override
    public Penalty addPenalty(Penalty penalty) {
        return penaltyDao.addPenalty(penalty);
    }
    @Transactional
    @Override
    public Penalty updatePenalty(Penalty penalty, int id) {
        Penalty penaltyToUpdate = penaltyDao.getPenalty(id);
        penaltyToUpdate.setCost(penalty.getCost());
        penaltyToUpdate.setName(penalty.getName());

        String month = LocalDateTime.now().getMonth().toString();
        String year = String.valueOf(LocalDateTime.now().getYear());
        List<RentPayment> rentPayments = tenantDao.getRentPaymentRows(month, year);

        for(RentPayment rentPayment : rentPayments) {
            rentPayment.setPenaltyActive(true);
            rentPayment.setPenaltyPerDay(penalty.getCost());
            tenantDao.updateRentPaymentRow(rentPayment);
        }

        return penaltyDao.updatePenalty(penaltyToUpdate);
    }

    @Override
    public Penalty getPenalty(int id) {
        return penaltyDao.getPenalty(id);
    }

    @Transactional
    @Override
    public String removePenalty(int id) {
        return penaltyDao.removePenalty(id);
    }
    @Transactional
    @Override
    public Penalty activatePenalty(int id) {
        return penaltyDao.activatePenalty(id);
    }
    @Transactional
    @Override
    public Penalty deactivatePenalty(int id) {
        return penaltyDao.deactivatePenalty(id);
    }
}

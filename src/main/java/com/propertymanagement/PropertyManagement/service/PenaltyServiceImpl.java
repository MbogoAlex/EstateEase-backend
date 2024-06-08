package com.propertymanagement.PropertyManagement.service;

import com.propertymanagement.PropertyManagement.dao.PenaltyDao;
import com.propertymanagement.PropertyManagement.entity.Penalty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PenaltyServiceImpl implements PenaltyService{
    private final PenaltyDao penaltyDao;
    @Autowired
    public PenaltyServiceImpl(PenaltyDao penaltyDao) {
        this.penaltyDao = penaltyDao;
    }
    @Transactional
    @Override
    public Penalty addPenalty(Penalty penalty) {
        return penaltyDao.addPenalty(penalty);
    }
    @Transactional
    @Override
    public Penalty updatePenalty(Penalty penalty) {
        return penaltyDao.updatePenalty(penalty);
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

package com.propertymanagement.PropertyManagement.dao;

import com.propertymanagement.PropertyManagement.entity.Penalty;

public interface PenaltyDao {
    Penalty addPenalty(Penalty penalty);

    Penalty getPenalty(int id);
    Penalty updatePenalty(Penalty penalty);
    String removePenalty(int id);
    Penalty activatePenalty(int id);
    Penalty deactivatePenalty(int id);
}

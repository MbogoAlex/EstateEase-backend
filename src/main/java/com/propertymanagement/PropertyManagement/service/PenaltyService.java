package com.propertymanagement.PropertyManagement.service;

import com.propertymanagement.PropertyManagement.entity.Penalty;

public interface PenaltyService {
    Penalty addPenalty(Penalty penalty);
    Penalty updatePenalty(Penalty penalty, int id);

    Penalty getPenalty(int id);
    String removePenalty(int id);
    Penalty activatePenalty(int id);
    Penalty deactivatePenalty(int id);
}

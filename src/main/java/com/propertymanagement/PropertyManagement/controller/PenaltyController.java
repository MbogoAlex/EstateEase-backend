package com.propertymanagement.PropertyManagement.controller;

import com.propertymanagement.PropertyManagement.dto.Response;
import com.propertymanagement.PropertyManagement.entity.Penalty;
import org.springframework.http.ResponseEntity;

public interface PenaltyController {
    ResponseEntity<Response> addPenalty(Penalty penalty);
    ResponseEntity<Response> updatePenalty(Penalty penalty, int id);

    ResponseEntity<Response> getPenalty(int id);
    ResponseEntity<Response> removePenalty(int id);
    ResponseEntity<Response> activatePenalty(int id);
    ResponseEntity<Response> deactivatePenalty(int id);
}

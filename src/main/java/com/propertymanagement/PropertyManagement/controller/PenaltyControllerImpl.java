package com.propertymanagement.PropertyManagement.controller;

import com.propertymanagement.PropertyManagement.dto.Response;
import com.propertymanagement.PropertyManagement.entity.Penalty;
import com.propertymanagement.PropertyManagement.service.PenaltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static java.util.Map.of;

@RestController
@RequestMapping("/api/")
public class PenaltyControllerImpl implements PenaltyController{

    private final PenaltyService penaltyService;
    @Autowired
    public PenaltyControllerImpl(PenaltyService penaltyService) {
        this.penaltyService = penaltyService;
    }
    @PostMapping("penalty")
    @Override
    public ResponseEntity<Response> addPenalty(@RequestBody Penalty penalty) {
        return buildResponse("penalty", penaltyService.addPenalty(penalty), "Penalty added", HttpStatus.CREATED);
    }
    @PutMapping("penalty")
    @Override
    public ResponseEntity<Response> updatePenalty(@RequestBody Penalty penalty) {
        return buildResponse("penalty", penaltyService.updatePenalty(penalty), "Penalty updated", HttpStatus.CREATED);
    }
    @GetMapping("penalty/{id}")
    @Override
    public ResponseEntity<Response> getPenalty(@PathVariable("id") int id) {
        return buildResponse("penalty", penaltyService.getPenalty(id), "Penalty fetched", HttpStatus.OK);
    }

    @DeleteMapping("penalty/{id}")
    @Override
    public ResponseEntity<Response> removePenalty(@PathVariable("id") int id) {
        return buildResponse("penalty", penaltyService.removePenalty(id), "Penalty removed", HttpStatus.OK);
    }
    @PutMapping("penalty/activate/{id}")
    @Override
    public ResponseEntity<Response> activatePenalty(@PathVariable("id") int id) {
        return buildResponse("penalty", penaltyService.activatePenalty(id), "Penalty activated", HttpStatus.OK);
    }
    @PutMapping("penalty/deactivate/{id}")
    @Override
    public ResponseEntity<Response> deactivatePenalty(@PathVariable("id")  int id) {
        return buildResponse("penalty", penaltyService.deactivatePenalty(id), "Penalty deactivated", HttpStatus.OK);
    }

    private ResponseEntity<Response> buildResponse(String desc, Object data, String message, HttpStatus status) {
        return ResponseEntity.status(status)
                .body(Response.builder()
                        .timestamp(LocalDateTime.now())
                        .data(data == null ? null : of(desc, data))
                        .message(message)
                        .status(status)
                        .statusCode(status.value())
                        .build());
    }
}

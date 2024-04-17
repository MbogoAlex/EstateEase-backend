package com.propertymanagement.PropertyManagement.controller;

import com.propertymanagement.PropertyManagement.dto.PManagerDTO;
import com.propertymanagement.PropertyManagement.dto.PManagerLoginDTO;
import com.propertymanagement.PropertyManagement.dto.Response;
import com.propertymanagement.PropertyManagement.dto.RoleDTO;
import com.propertymanagement.PropertyManagement.entity.PManager;
import com.propertymanagement.PropertyManagement.entity.Role;
import com.propertymanagement.PropertyManagement.service.PManagerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static java.util.Map.of;

@RestController
@RequestMapping("/api")
public class PManagerControllerImpl implements PManagerController {
    private PManagerServiceImpl pManagerService;

    @Autowired
    public PManagerControllerImpl(PManagerServiceImpl pManagerService) {
        this.pManagerService = pManagerService;
    }

    @PostMapping("/role")
    public ResponseEntity<Response> addNewRole(@RequestBody RoleDTO roleDTO) {
        return buildResponse("role", pManagerService.addNewRole(roleDTO), "Created successfully", HttpStatus.CREATED) ;
    }

    @DeleteMapping ("/role/{id}")
    public String removeRole(@PathVariable("id") int roleId) {
        return pManagerService.removeRole(roleId);
    }


    @DeleteMapping("/pmanager/{id}")
    public String removePropertyManager(@PathVariable("id") int id) {
        return pManagerService.removePropertyManager(id);
    }

    @Override
    @PostMapping("/pmanager")
    public ResponseEntity<Response> addNewPManager(@RequestBody PManagerDTO pManagerDTO) {
        return buildResponse("pmanager", pManagerService.addNewPManager(pManagerDTO), "Created succesfully", HttpStatus.CREATED) ;
    }

    @Override
    @PutMapping("/pmanager/pmanagerId={pmanagerId}")
    public ResponseEntity<Response> updatePManager(@RequestBody PManagerDTO pManagerDTO, @PathVariable("pmanagerId") int pManagerId) {
        return buildResponse("manager", pManagerService.updatePManager(pManagerDTO, pManagerId), "Updated successfully", HttpStatus.OK);
    }

    @Override
    @PutMapping("/pmnager/archive/pmanagerId={pmanagerId}")
    public ResponseEntity<Response> archivePManager(@PathVariable("pmanagerId") int pManagerId) {
        return buildResponse("pmanager", pManagerService.archivePManager(pManagerId), "Archived successfully", HttpStatus.OK);
    }

    @Override
    @PostMapping("/pmanager/login")
    public ResponseEntity<Response> pManagerLogin(@RequestBody PManagerLoginDTO pManagerLoginDTO) {
        return buildResponse("pmanager", pManagerService.pManagerLogin(pManagerLoginDTO), "Login successful", HttpStatus.OK);
    }
    @GetMapping("/rentpayment/overview/month={month}/year={year}")
    @Override
    public ResponseEntity<Response> getRentPayments(@PathVariable("month") String month, @PathVariable("year") String year) {
        return buildResponse("rentpayment", pManagerService.getRentPaymentOverview(month, year), "Fetched successfully", HttpStatus.OK);
    }
    @GetMapping("rentpaymen/detailed")

    @Override
    public ResponseEntity<Response> getRentPaymentDetailedInfo(
            @RequestParam(value = "month") String month,
            @RequestParam(value = "year") String year,
            @RequestParam(value = "rooms", required = false) Integer rooms,
            @RequestParam(value = "roomName", required = false) String roomName,
            @RequestParam(value = "tenantName", required = false) String tenantName
            ) {
        return buildResponse("rentpayment", pManagerService.getRentPaymentDetailedInfo(month, year, rooms, roomName, tenantName), "Fetched successfully", HttpStatus.OK);
    }
    @GetMapping("rentpayment/detailed")
    @Override
    public ResponseEntity<Response> getDetailedRentPayments(
            @RequestParam("month") String month,
            @RequestParam("year") String year,
            @RequestParam(value = "roomName", required = false) String roomName,
            @RequestParam(value = "rooms", required = false) Integer rooms,
            @RequestParam(value = "tenantName", required = false) String tenantName,
            @RequestParam(value = "tenantId", required = false) Integer tenantId,
            @RequestParam(value = "rentPaymentStatus", required = false) Boolean rentPaymentStatus,
            @RequestParam(value = "paidLate", required = false) Boolean paidLate
    ) {
        return buildResponse("rentpayment", pManagerService.getDetailedRentPayments(month, year, roomName, rooms, tenantName, tenantId, rentPaymentStatus, paidLate), "Fetched successfully", HttpStatus.OK);
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

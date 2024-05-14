package com.propertymanagement.PropertyManagement.controller;

import com.propertymanagement.PropertyManagement.dto.*;
import com.propertymanagement.PropertyManagement.entity.Tenant;
import com.propertymanagement.PropertyManagement.service.TenantService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;

import static java.util.Map.of;

@RestController
@RequestMapping("/api")
public class TenantControllerImpl implements TenantController{
    private TenantService tenantService;

    public TenantControllerImpl(TenantService tenantService) {
        this.tenantService = tenantService;
    }
    @Override
    @PostMapping("/tenant")
    public ResponseEntity<Response> addNewTenant(@RequestBody TenantDTO tenantDTO) {
        return buildResponse("tenant", tenantService.addNewTenant(tenantDTO), "Created successfully", HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/tenant")
    public ResponseEntity<Response> updateTenant(@RequestBody TenantUpdateDTO tenantDTO) {
        return buildResponse("tenant", tenantService.updateTenant(tenantDTO), "Updated successfully", HttpStatus.OK);
    }

    @Override
    @GetMapping("/tenant")
    public ResponseEntity<Response> getAllTenants() {
        return buildResponse("tenant", tenantService.getAllTenants(), "Fetched successfully", HttpStatus.OK);
    }

    @Override
    @GetMapping("/tenant/tenantId={tenantId}")
    public ResponseEntity<Response> getTenantByTenantId(@PathVariable("tenantId") int tenantId) {
        return buildResponse("tenant", tenantService.getTenantByTenantId(tenantId), "Fetched successfully", HttpStatus.OK);
    }

    @Override
    @PostMapping("/rentpayment/alltenants/newrow")
    public ResponseEntity<Response> addNewRentPaymentRow(@RequestBody RentPaymentDTO rentPaymentDTO) {
        return buildResponse("rentPayment", tenantService.addNewRentPaymentRow(rentPaymentDTO ), "Created successfully", HttpStatus.OK);
    }


    @Override
    @PostMapping("/rentpayment/rentPaymentTblId={id}")
    public ResponseEntity<Response> payRent(@RequestBody RentPaymentRequestDTO rentPaymentRequestDTO, @PathVariable("id") int rentPaymentTblId) {
        return buildResponse("rentPayment", tenantService.payRent(rentPaymentRequestDTO, rentPaymentTblId), "Paid successfully", HttpStatus.OK);
    }

    @Override
    @PutMapping("/tenant/archive/tenantId={tenantId}/propertyId={propertyId}")
    public ResponseEntity<Response> archiveTenant(@PathVariable("tenantId") int tenantId, @PathVariable("propertyId") int propertyId) {
        return buildResponse("tenant", tenantService.archiveTenant(tenantId, propertyId), "Archived successfully", HttpStatus.OK);
    }

    @Override
    @PostMapping("/tenant/login")
    public ResponseEntity<Response> tenantLogin(@RequestBody TenantLoginDTO tenantLoginDTO) {
        return buildResponse("tenant", tenantService.tenantLogin(tenantLoginDTO), "Login successful", HttpStatus.OK);
    }

    @Override
    @PutMapping("/tenant/penalty/activate/rentPaymentTblId={rentPaymentTblId}")
    public ResponseEntity<Response> activateLatePaymentPenaltyForSingleTenant(@RequestBody RentPenaltyDTO rentPenaltyDTO, @PathVariable("rentPaymentTblId") int rentPaymentTblId) {
        return buildResponse("rentpayment", tenantService.activateLatePaymentPenaltyForSingleTenant(rentPenaltyDTO, rentPaymentTblId), "Penalty activated", HttpStatus.OK);
    }

    @Override
    @PutMapping("/tenant/penalty/deactivate/rentPaymentTblId={rentPaymentTblId}")
    public ResponseEntity<Response> deActivateLatePaymentPenaltyForSingleTenant(@PathVariable("rentPaymentTblId") int rentPaymentTblId) {
        return buildResponse("rentpayment", tenantService.deActivateLatePaymentPenaltyForSingleTenant(rentPaymentTblId), "Penalty deactivated", HttpStatus.OK);
    }


    @Override
    @PutMapping("/tenant/penalty/activate/month={month}/year={year}")
    public ResponseEntity<Response> activateLatePaymentPenaltyForMultipleTenants(@RequestBody RentPenaltyDTO rentPenaltyDTO, @PathVariable("month") String month, @PathVariable("year") String year) {
        return buildResponse("rentpayment", tenantService.activateLatePaymentPenaltyForMultipleTenants(rentPenaltyDTO, month, year), "Penalty activated", HttpStatus.OK);
    }

    @Override
    @PutMapping("/tenant/penalty/deactivate/month={month}/year={year}")
    public ResponseEntity<Response> deActivateLatePaymentPenaltyForMultipleTenants(@PathVariable("month") String month, @PathVariable("year") String year) {
        return buildResponse("rentpayment", tenantService.deActivateLatePaymentPenaltyForMultipleTenants(month, year), "Penalty deactivated", HttpStatus.OK);
    }

    @Override
    @GetMapping("/tenant/tenantrentpaymentrow")
    public ResponseEntity<Response> getRentPaymentRowsByTenantId(
            @RequestParam(value = "tenantId") Integer tenantId,
            @RequestParam(value = "month", required = false) String month,
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "roomName", required = false) String roomName,
            @RequestParam(value = "rooms", required = false) Integer rooms,
            @RequestParam(value = "tenantName", required = false) String tenantName,
            @RequestParam(value = "rentPaymentStatus", required = false) Boolean rentPaymentStatus,
            @RequestParam(value = "paidLate", required = false) Boolean paidLate,
            @RequestParam(value = "tenantActive", required = false) Boolean tenantActive) throws JRException {
        return buildResponse("rentpayment", tenantService.getRentPaymentRowsByTenantId(tenantId, month, year, roomName, rooms, tenantName, rentPaymentStatus, paidLate, tenantActive), "Fetched successfully", HttpStatus.OK);
    }

    @Override
    @GetMapping("/tenant/rentpaymentsreport")
    public ResponseEntity<Response> generateRentPaymentsReport(
            @RequestParam(value = "tenantId") Integer tenantId,
            @RequestParam(value = "month", required = false) String month,
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "roomName", required = false) String roomName,
            @RequestParam(value = "rooms", required = false) Integer rooms,
            @RequestParam(value = "tenantName", required = false) String tenantName,
            @RequestParam(value = "rentPaymentStatus", required = false) Boolean rentPaymentStatus,
            @RequestParam(value = "paidLate", required = false) Boolean paidLate,
            @RequestParam(value = "tenantActive", required = false) Boolean tenantActive
    ) throws JRException {
        ByteArrayOutputStream reportStream = tenantService.generateRentPaymentsReport(tenantId, month, year, roomName, rooms, tenantName, rentPaymentStatus, paidLate, tenantActive);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);
        return new ResponseEntity(reportStream.toByteArray(), httpHeaders, HttpStatus.OK);
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

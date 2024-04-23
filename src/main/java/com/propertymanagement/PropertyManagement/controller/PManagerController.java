package com.propertymanagement.PropertyManagement.controller;

import com.propertymanagement.PropertyManagement.dto.DetailedRentPaymentInfoDTO;
import com.propertymanagement.PropertyManagement.dto.PManagerDTO;
import com.propertymanagement.PropertyManagement.dto.PManagerLoginDTO;
import com.propertymanagement.PropertyManagement.dto.Response;
import com.propertymanagement.PropertyManagement.entity.RentPayment;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface PManagerController {
    // add pManager
    ResponseEntity<Response> addNewPManager(PManagerDTO pManagerDTO);

    // updatePManager

    ResponseEntity<Response> updatePManager(PManagerDTO pManagerDTO, int pManagerId);

    // archivePManager

    ResponseEntity<Response> archivePManager(int pManagerId);

    // login pmanager
    ResponseEntity<Response> pManagerLogin(PManagerLoginDTO pManagerLoginDTO);

    ResponseEntity<Response> getRentPayments(String month, String year);

    ResponseEntity<Response> getRentPaymentDetailedInfo(String month, String year, Integer rooms, String roomName, String tenantName);

    ResponseEntity<Response> getDetailedRentPayments(String month, String year, String roomName, Integer rooms, String tenantName, Integer tenantId, Boolean rentPaymentStatus, Boolean paidLate, Boolean tenantActive);
}

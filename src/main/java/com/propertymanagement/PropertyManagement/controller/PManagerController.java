package com.propertymanagement.PropertyManagement.controller;

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
}

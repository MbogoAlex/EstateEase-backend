package com.propertymanagement.PropertyManagement.controller;

import com.propertymanagement.PropertyManagement.dto.PManagerDTO;
import com.propertymanagement.PropertyManagement.dto.PManagerLoginDTO;
import com.propertymanagement.PropertyManagement.dto.Response;
import org.springframework.http.ResponseEntity;

public interface PManagerController {
    // add pManager
    ResponseEntity<Response> addNewPManager(PManagerDTO pManagerDTO);

    // updatePManager

    ResponseEntity<Response> updatePManager(PManagerDTO pManagerDTO, int pManagerId);

    // archivePManager

    ResponseEntity<Response> archivePManager(int pManagerId);

    // login pmanager
    ResponseEntity<Response> pManagerLogin(PManagerLoginDTO pManagerLoginDTO);
}

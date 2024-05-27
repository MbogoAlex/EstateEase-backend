package com.propertymanagement.PropertyManagement.controller;

import com.propertymanagement.PropertyManagement.dto.CaretakerAccountDTO;
import com.propertymanagement.PropertyManagement.dto.CaretakerLoginDTO;
import com.propertymanagement.PropertyManagement.dto.Response;
import org.springframework.http.ResponseEntity;

public interface CaretakerController {
    public ResponseEntity<Response> createAccount(CaretakerAccountDTO caretakerAccountDTO);
    ResponseEntity<Response> login(CaretakerLoginDTO caretakerLoginDTO);
    ResponseEntity<Response> deregisterCaretaker(int id);
}

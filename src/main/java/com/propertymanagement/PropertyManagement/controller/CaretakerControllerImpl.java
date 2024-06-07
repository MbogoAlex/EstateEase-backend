package com.propertymanagement.PropertyManagement.controller;

import com.propertymanagement.PropertyManagement.dto.CaretakerAccountDTO;
import com.propertymanagement.PropertyManagement.dto.CaretakerLoginDTO;
import com.propertymanagement.PropertyManagement.dto.Response;
import com.propertymanagement.PropertyManagement.service.CaretakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static java.util.Map.of;

@RestController
@RequestMapping("/api/")
public class CaretakerControllerImpl implements CaretakerController{
    private CaretakerService caretakerService;
    @Autowired
    public CaretakerControllerImpl(CaretakerService caretakerService) {
        this.caretakerService = caretakerService;
    }
    @PostMapping("caretaker/register")
    @Override
    public ResponseEntity<Response> createAccount(@RequestBody CaretakerAccountDTO caretakerAccountDTO) {
        return buildResponse("caretaker", caretakerService.createAccount(caretakerAccountDTO), "Account created", HttpStatus.CREATED);
    }
    @PostMapping("caretaker/login")
    @Override
    public ResponseEntity<Response> login(@RequestBody CaretakerLoginDTO caretakerLoginDTO) {
        return buildResponse("caretaker", caretakerService.login(caretakerLoginDTO), "Login successful", HttpStatus.OK);
    }
    @PutMapping("caretaker/deregister/caretakerId={caretakerId}")
    @Override
    public ResponseEntity<Response> deregisterCaretaker(@PathVariable("caretakerId") int id) {
        return buildResponse("caretaker", caretakerService.deregisterCaretaker(id), "Caretaker removed", HttpStatus.OK);
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

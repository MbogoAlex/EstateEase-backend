package com.propertymanagement.PropertyManagement.controller;

import com.propertymanagement.PropertyManagement.dto.Response;
import com.propertymanagement.PropertyManagement.entity.Wallet;
import com.propertymanagement.PropertyManagement.service.WalletService;
import com.propertymanagement.PropertyManagement.service.WalletServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static java.util.Map.of;

@RestController
@RequestMapping("/api/")
public class WalletControllerImpl implements WalletController{
    private final WalletService walletService;
    @Autowired
    public WalletControllerImpl(WalletService walletService) {
        this.walletService = walletService;
    }
    @GetMapping("wallet/{id}")
    @Override
    public ResponseEntity<Response> getWallet(@PathVariable("id") int id) {
        return buildResponse("wallet", walletService.getWallet(id), "Wallet fetched", HttpStatus.OK);
    }
    @PutMapping("wallet/{id}")
    @Override
    public ResponseEntity<Response> updateWallet(@PathVariable("id") int id) {
        return buildResponse("wallet", walletService.updateWallet(id), "Wallet updated", HttpStatus.CREATED);
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

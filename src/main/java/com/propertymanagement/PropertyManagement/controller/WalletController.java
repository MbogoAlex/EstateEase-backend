package com.propertymanagement.PropertyManagement.controller;

import com.propertymanagement.PropertyManagement.dto.Response;
import com.propertymanagement.PropertyManagement.entity.Wallet;
import org.springframework.http.ResponseEntity;

public interface WalletController {
    ResponseEntity<Response> getWallet(int id);
    ResponseEntity<Response> updateWallet(int id);
}

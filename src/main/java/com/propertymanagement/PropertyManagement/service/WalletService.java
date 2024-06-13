package com.propertymanagement.PropertyManagement.service;

import com.propertymanagement.PropertyManagement.entity.Wallet;

public interface WalletService {
    Wallet getWallet(int id);
    Wallet updateWallet(int id);
}

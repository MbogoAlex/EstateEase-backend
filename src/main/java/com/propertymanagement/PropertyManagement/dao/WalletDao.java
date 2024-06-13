package com.propertymanagement.PropertyManagement.dao;

import com.propertymanagement.PropertyManagement.entity.Wallet;

public interface WalletDao {
    Wallet getWallet(int id);
    Wallet updateWallet(Wallet wallet);
}

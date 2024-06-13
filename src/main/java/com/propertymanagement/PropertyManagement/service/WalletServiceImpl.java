package com.propertymanagement.PropertyManagement.service;

import com.propertymanagement.PropertyManagement.dao.WalletDao;
import com.propertymanagement.PropertyManagement.dao.WalletDaoImpl;
import com.propertymanagement.PropertyManagement.entity.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService{
    private final WalletDao walletDao;
    @Autowired
    public WalletServiceImpl(WalletDao walletDao) {
        this.walletDao = walletDao;
    }
    @Override
    public Wallet getWallet(int id) {
        return walletDao.getWallet(id);
    }

    @Override
    public Wallet updateWallet(int id) {
        Wallet wallet = walletDao.getWallet(id);
        return walletDao.updateWallet(wallet);
    }
}

package com.propertymanagement.PropertyManagement.dao;

import com.propertymanagement.PropertyManagement.entity.Wallet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class WalletDaoImpl implements WalletDao{
    private final EntityManager entityManager;
    @Autowired
    public WalletDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public Wallet getWallet(int id) {
        TypedQuery<Wallet> query = entityManager.createQuery("from Wallet where id = :id", Wallet.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public Wallet updateWallet(Wallet wallet) {
        entityManager.merge(wallet);
        return wallet;
    }
}

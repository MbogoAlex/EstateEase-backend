package com.propertymanagement.PropertyManagement.dao;

import com.propertymanagement.PropertyManagement.entity.Caretaker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CaretakerDaoImpl implements CaretakerDao{

    private EntityManager entityManager;
    @Autowired
    public CaretakerDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public Caretaker createAccount(Caretaker caretaker) {
        entityManager.persist(caretaker);
        return caretaker;
    }


    @Override
    public Caretaker findCaretakerById(int id) {
        TypedQuery<Caretaker> query = entityManager.createQuery("from Caretaker where caretakerId = :id", Caretaker.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public Caretaker findCaretakerByPhoneNumberAndPassword(String phoneNumber, String password) {
        TypedQuery<Caretaker> query = entityManager.createQuery("from Caretaker where phoneNumber = :phoneNumber and password = :password", Caretaker.class);
        query.setParameter("phoneNumber", phoneNumber);
        query.setParameter("password", password);
        return query.getSingleResult();
    }

    @Override
    public Caretaker deregisterCaretaker(int id) {
        TypedQuery<Caretaker> query = entityManager.createQuery("from Caretaker where caretakerId = :id", Caretaker.class);
        query.setParameter("id", id);
        Caretaker caretaker = query.getSingleResult();
        caretaker.setActive(false);
        entityManager.merge(caretaker);
        return caretaker;
    }
}

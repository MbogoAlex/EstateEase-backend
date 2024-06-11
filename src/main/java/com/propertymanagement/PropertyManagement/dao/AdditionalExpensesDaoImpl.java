package com.propertymanagement.PropertyManagement.dao;

import com.propertymanagement.PropertyManagement.entity.AdditionalExpenses;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdditionalExpensesDaoImpl implements AdditionalExpensesDao{
    private final EntityManager entityManager;
    @Autowired
    public AdditionalExpensesDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public AdditionalExpenses addExpense(AdditionalExpenses additionalExpenses) {
        entityManager.persist(additionalExpenses);
        return additionalExpenses;
    }

    @Override
    public AdditionalExpenses updateExpense(AdditionalExpenses additionalExpenses) {
        entityManager.merge(additionalExpenses);
        return additionalExpenses;
    }

    @Override
    public AdditionalExpenses getExpense(int id) {
        TypedQuery<AdditionalExpenses> query = entityManager.createQuery("from AdditionalExpenses where id = :id", AdditionalExpenses.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public String deleteExpense(int id) {
        Query query = entityManager.createQuery("delete from AdditionalExpenses where id = :id");
        query.setParameter("id", id);
        return "Deleted "+query.executeUpdate()+" rows";
    }
}

package com.propertymanagement.PropertyManagement.service;

import com.propertymanagement.PropertyManagement.dao.AdditionalExpensesDao;
import com.propertymanagement.PropertyManagement.entity.AdditionalExpenses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdditionalExpenseServiceImpl implements AdditionalExpenseService {
    private final AdditionalExpensesDao additionalExpensesDao;
    @Autowired
    public AdditionalExpenseServiceImpl(AdditionalExpensesDao additionalExpensesDao) {
        this.additionalExpensesDao = additionalExpensesDao;
    }
    @Transactional
    @Override
    public AdditionalExpenses addExpense(AdditionalExpenses additionalExpenses) {
        return additionalExpensesDao.addExpense(additionalExpenses);
    }
    @Transactional
    @Override
    public AdditionalExpenses updateExpense(AdditionalExpenses additionalExpenses, int id) {
        AdditionalExpenses additionalExpenses1 = additionalExpensesDao.getExpense(id);
        additionalExpenses1.setName(additionalExpenses.getName());
        additionalExpenses1.setCost(additionalExpenses.getCost());
        return additionalExpensesDao.updateExpense(additionalExpenses1);
    }

    @Override
    public AdditionalExpenses getExpense(int id) {
        return additionalExpensesDao.getExpense(id);
    }
    @Transactional
    @Override
    public String deleteExpense(int id) {
        return additionalExpensesDao.deleteExpense(id);
    }
}

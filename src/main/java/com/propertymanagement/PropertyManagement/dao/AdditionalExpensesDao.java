package com.propertymanagement.PropertyManagement.dao;

import com.propertymanagement.PropertyManagement.entity.AdditionalExpenses;

public interface AdditionalExpensesDao {
    AdditionalExpenses addExpense(AdditionalExpenses additionalExpenses);
    AdditionalExpenses updateExpense(AdditionalExpenses additionalExpenses);

    AdditionalExpenses getExpense(int id);
    String deleteExpense(int id);
}

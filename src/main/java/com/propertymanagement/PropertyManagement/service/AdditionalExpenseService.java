package com.propertymanagement.PropertyManagement.service;

import com.propertymanagement.PropertyManagement.entity.AdditionalExpenses;

public interface AdditionalExpenseService {
    AdditionalExpenses addExpense(AdditionalExpenses additionalExpenses);
    AdditionalExpenses updateExpense(AdditionalExpenses additionalExpenses, int id);
    AdditionalExpenses getExpense(int id);
    String deleteExpense(int id);
}

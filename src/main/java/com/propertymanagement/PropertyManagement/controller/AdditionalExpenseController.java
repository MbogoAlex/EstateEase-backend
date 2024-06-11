package com.propertymanagement.PropertyManagement.controller;

import com.propertymanagement.PropertyManagement.dto.Response;
import com.propertymanagement.PropertyManagement.entity.AdditionalExpenses;
import org.springframework.http.ResponseEntity;

public interface AdditionalExpenseController {
    ResponseEntity<Response> addExpense(AdditionalExpenses additionalExpenses);
    ResponseEntity<Response> updateExpense(AdditionalExpenses additionalExpenses, int id);
    ResponseEntity<Response> getExpense(int id);
    ResponseEntity<Response> deleteExpense(int id);
}

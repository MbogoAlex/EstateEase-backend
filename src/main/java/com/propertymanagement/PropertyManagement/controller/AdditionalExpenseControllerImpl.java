package com.propertymanagement.PropertyManagement.controller;

import com.propertymanagement.PropertyManagement.dto.Response;
import com.propertymanagement.PropertyManagement.entity.AdditionalExpenses;
import com.propertymanagement.PropertyManagement.service.AdditionalExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static java.util.Map.of;
@RestController
@RequestMapping("/api/")
public class AdditionalExpenseControllerImpl implements AdditionalExpenseController{
    private final AdditionalExpenseService additionalExpenseService;
    @Autowired
    public AdditionalExpenseControllerImpl(AdditionalExpenseService additionalExpenseService) {
        this.additionalExpenseService = additionalExpenseService;
    }
    @PostMapping("expense")
    @Override
    public ResponseEntity<Response> addExpense(@RequestBody AdditionalExpenses additionalExpenses) {
        return buildResponse("expense", additionalExpenseService.addExpense(additionalExpenses), "Expense added", HttpStatus.CREATED);
    }
    @PutMapping("expense/{id}")
    @Override
    public ResponseEntity<Response> updateExpense(
            @RequestBody AdditionalExpenses additionalExpenses,
            @PathVariable("id") int id
    ) {
        return buildResponse("expense", additionalExpenseService.updateExpense(additionalExpenses, id), "Expense updated", HttpStatus.OK);
    }
    @GetMapping("expense/{id}")
    @Override
    public ResponseEntity<Response> getExpense(@PathVariable("id") int id) {
        return buildResponse("expense", additionalExpenseService.getExpense(id), "Expense fetched", HttpStatus.OK);
    }
    @DeleteMapping("expense/{id}")
    @Override
    public ResponseEntity<Response> deleteExpense(@PathVariable("id") int id) {
        return buildResponse("expense", additionalExpenseService.deleteExpense(id), "Expense deleted", HttpStatus.OK);
    }

    private ResponseEntity<Response> buildResponse(String desc, Object data, String message, HttpStatus status) {
        return ResponseEntity.status(status)
                .body(Response.builder()
                        .timestamp(LocalDateTime.now())
                        .data(data == null ? null : of(desc, data))
                        .message(message)
                        .status(status)
                        .statusCode(status.value())
                        .build());
    }
}

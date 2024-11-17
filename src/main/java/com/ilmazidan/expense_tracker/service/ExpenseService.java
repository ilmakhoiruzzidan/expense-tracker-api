package com.ilmazidan.expense_tracker.service;

import com.ilmazidan.expense_tracker.dto.request.ExpenseRequest;
import com.ilmazidan.expense_tracker.dto.request.PagingAndSortRequest;
import com.ilmazidan.expense_tracker.dto.response.ExpenseResponse;
import com.ilmazidan.expense_tracker.entity.Expense;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface ExpenseService {
    ExpenseResponse createExpense(ExpenseRequest request);

    ExpenseResponse getExpenseById(String id);

    Page<ExpenseResponse> getAllExpenses(PagingAndSortRequest request);

    Page<ExpenseResponse> getExpenseByDate(PagingAndSortRequest request, LocalDateTime startDate, LocalDateTime endDate);

    ExpenseResponse updateExpense(ExpenseRequest request, String id);

    ExpenseResponse deleteExpense(String id);

    Expense getOne(String id);

}

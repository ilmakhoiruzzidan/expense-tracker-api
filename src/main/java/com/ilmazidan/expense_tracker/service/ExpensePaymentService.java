package com.ilmazidan.expense_tracker.service;

import com.ilmazidan.expense_tracker.dto.request.ExpensePaymentRequest;
import com.ilmazidan.expense_tracker.dto.request.PagingAndSortRequest;
import com.ilmazidan.expense_tracker.dto.response.ExpensePaymentResponse;
import com.ilmazidan.expense_tracker.entity.ExpensePayment;
import org.springframework.data.domain.Page;

public interface ExpensePaymentService {
    ExpensePaymentResponse createExpensePayment(ExpensePaymentRequest request);
    ExpensePaymentResponse getExpensePaymentById(String id);
    Page<ExpensePaymentResponse> getAllExpensePayments(PagingAndSortRequest request);
    void deleteExpensePaymentById(String id);
    ExpensePaymentResponse updateExpensePayment(ExpensePaymentRequest request, String id);
    ExpensePayment getOne(String id);

}

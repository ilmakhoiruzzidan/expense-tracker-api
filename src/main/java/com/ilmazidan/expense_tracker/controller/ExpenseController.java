package com.ilmazidan.expense_tracker.controller;

import com.ilmazidan.expense_tracker.dto.request.ExpenseRequest;
import com.ilmazidan.expense_tracker.dto.request.PagingAndSortRequest;
import com.ilmazidan.expense_tracker.dto.response.ExpenseResponse;
import com.ilmazidan.expense_tracker.service.ExpenseService;
import com.ilmazidan.expense_tracker.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<?> createExpense(@RequestBody ExpenseRequest request) {
        ExpenseResponse expenseResponse = expenseService.createExpense(request);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully created expense", expenseResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getExpenseById(@PathVariable String id) {
        ExpenseResponse expenseResponse = expenseService.getExpenseById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully retrieved expense", expenseResponse);
    }

    @GetMapping
    public ResponseEntity<?> getAllExpenses(
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size
    ) {
        PagingAndSortRequest pagingAndSortRequest = PagingAndSortRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .build();

        Page<ExpenseResponse> expenseResponses = expenseService.getAllExpenses(pagingAndSortRequest);
        return ResponseUtil.buildResponsePagination(HttpStatus.OK, "Successfully retrieved expenses", expenseResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExpense(@PathVariable String id,@RequestBody ExpenseRequest request) {
        ExpenseResponse expenseResponse = expenseService.updateExpense(request, id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully updated expense", expenseResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable String id) {
        ExpenseResponse expenseResponse = expenseService.deleteExpense(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully deleted expense", null);
    }
}

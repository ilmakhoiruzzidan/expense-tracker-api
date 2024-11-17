package com.ilmazidan.expense_tracker.controller;

import com.ilmazidan.expense_tracker.constant.Constant;
import com.ilmazidan.expense_tracker.dto.request.ExpenseRequest;
import com.ilmazidan.expense_tracker.dto.request.PagingAndSortRequest;
import com.ilmazidan.expense_tracker.dto.response.ExpenseResponse;
import com.ilmazidan.expense_tracker.service.ExpenseService;
import com.ilmazidan.expense_tracker.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.ilmazidan.expense_tracker.constant.Constant.*;

@RestController
@RequestMapping(Constant.EXPENSES_API)
@RequiredArgsConstructor
@Tag(name = "Expense Controller", description = "Manage expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    @Operation(summary = "Create an expense", description = "Add a new expense to the system")
    @PostMapping
    public ResponseEntity<?> createExpense(@RequestBody ExpenseRequest request) {
        ExpenseResponse expenseResponse = expenseService.createExpense(request);
        return ResponseUtil.buildResponse(HttpStatus.OK, SUCCESS_CREATE_EXPENSE, expenseResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getExpenseById(@PathVariable String id) {
        ExpenseResponse expenseResponse = expenseService.getExpenseById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, SUCCESS_RETRiEVE_EXPENSE, expenseResponse);
    }

    @GetMapping("/by-date-range")
    public ResponseEntity<?> getExpenseByDateRange(
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "startDate") String startDate,
            @RequestParam(name = "endDate") String endDate
    ) {
        PagingAndSortRequest pagingAndSortRequest = PagingAndSortRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .build();

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(23, 59, 59);
        Page<ExpenseResponse> expenseResponses = expenseService.getExpenseByDate(pagingAndSortRequest, startDateTime, endDateTime);
        return ResponseUtil.buildResponsePagination(HttpStatus.OK, SUCCESS_RETRIEVE_ALL_EXPENSES, expenseResponses);
    }

    @Operation(summary = "Get all expenses", description = "Retrieve a paginated list of all expenses")
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
        return ResponseUtil.buildResponsePagination(HttpStatus.OK, SUCCESS_RETRIEVE_ALL_EXPENSES, expenseResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExpense(@PathVariable String id,@RequestBody ExpenseRequest request) {
        ExpenseResponse expenseResponse = expenseService.updateExpense(request, id);
        return ResponseUtil.buildResponse(HttpStatus.OK, SUCCESS_UPDATE_EXPENSE, expenseResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable String id) {
        ExpenseResponse expenseResponse = expenseService.deleteExpense(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, SUCCESS_DELETE_EXPENSE, null);
    }
}

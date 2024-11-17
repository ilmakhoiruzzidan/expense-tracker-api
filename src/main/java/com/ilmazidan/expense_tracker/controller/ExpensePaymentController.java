package com.ilmazidan.expense_tracker.controller;

import com.ilmazidan.expense_tracker.constant.Constant;
import com.ilmazidan.expense_tracker.dto.request.ExpensePaymentRequest;
import com.ilmazidan.expense_tracker.dto.request.PagingAndSortRequest;
import com.ilmazidan.expense_tracker.dto.response.ExpensePaymentResponse;
import com.ilmazidan.expense_tracker.service.ExpensePaymentService;
import com.ilmazidan.expense_tracker.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ilmazidan.expense_tracker.constant.Constant.*;

@RestController
@RequestMapping(Constant.EXPENSE_PAYMENTS_API)
@RequiredArgsConstructor
public class ExpensePaymentController {
    private final ExpensePaymentService expensePaymentService;

    @PostMapping
    public ResponseEntity<?> createExpensePayment(@RequestBody ExpensePaymentRequest request) {
        ExpensePaymentResponse expensePaymentResponse = expensePaymentService.createExpensePayment(request);
        return ResponseUtil.buildResponse(HttpStatus.OK, SUCCESS_CREATE_EXPENSE_PAYMENT, expensePaymentResponse);
    }

    @GetMapping
    public ResponseEntity<?> getAllExpensePayments(
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size
    ) {
        PagingAndSortRequest pagingAndSortRequest = PagingAndSortRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .build();
        Page<ExpensePaymentResponse> expensePaymentResponses = expensePaymentService.getAllExpensePayments(pagingAndSortRequest);
        return ResponseUtil.buildResponsePagination(HttpStatus.OK, SUCCESS_RETRIEVE_ALL_EXPENSE_PAYMENTS, expensePaymentResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findExpensePayment(@PathVariable String id) {
        ExpensePaymentResponse expensePaymentResponse = expensePaymentService.getExpensePaymentById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, SUCCESS_RETRiEVE_EXPENSE_PAYMENT, expensePaymentResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExpensePayment(@PathVariable String id, @RequestBody ExpensePaymentRequest request) {
        ExpensePaymentResponse expensePaymentResponse = expensePaymentService.updateExpensePayment(request, id);
        return ResponseUtil.buildResponse(HttpStatus.OK, SUCCESS_UPDATE_EXPENSE_PAYMENT, expensePaymentResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpensePayment(@PathVariable String id) {
        expensePaymentService.deleteExpensePaymentById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, SUCCESS_DELETE_EXPENSE_PAYMENT, null);
    }
}

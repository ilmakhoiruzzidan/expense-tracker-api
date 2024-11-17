package com.ilmazidan.expense_tracker.controller;

import com.ilmazidan.expense_tracker.constant.Constant;
import com.ilmazidan.expense_tracker.dto.request.ExpenseRequest;
import com.ilmazidan.expense_tracker.dto.request.PagingAndSortRequest;
import com.ilmazidan.expense_tracker.dto.response.ExpenseResponse;
import com.ilmazidan.expense_tracker.service.ExpenseService;
import com.ilmazidan.expense_tracker.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Expense Management", description = "Manage expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    @Operation(
            summary = "Create an expense",
            description = "Add a new expense to the system",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Expense data to be created",
                    content = @Content(schema = @Schema(implementation = ExpenseRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Expense created successfully",
                            content = @Content(schema = @Schema(implementation = ExpenseResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
            }
    )
    @PostMapping
    public ResponseEntity<?> createExpense(@RequestBody ExpenseRequest request) {
        ExpenseResponse expenseResponse = expenseService.createExpense(request);
        return ResponseUtil.buildResponse(HttpStatus.OK, SUCCESS_CREATE_EXPENSE, expenseResponse);
    }

    @Operation(
            summary = "Get an expense by ID",
            description = "Retrieve a single expense by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Expense retrieved successfully",
                            content = @Content(schema = @Schema(implementation = ExpenseResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Expense not found"),
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getExpenseById(@PathVariable String id) {
        ExpenseResponse expenseResponse = expenseService.getExpenseById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, SUCCESS_RETRiEVE_EXPENSE, expenseResponse);
    }

    @Operation(
            summary = "Get expenses by date range",
            description = "Retrieve a paginated list of expenses filtered by date range",
            parameters = {
                    @Parameter(name = "startDate", description = "Start date in the format yyyy-MM-dd", example = "2024-03-01"),
                    @Parameter(name = "endDate", description = "End date in the format yyyy-MM-dd", example = "2024-09-30"),
                    @Parameter(name = "sortBy", description = "Sort field", example = "expenseDate"),
                    @Parameter(name = "page", description = "Page number", example = "1"),
                    @Parameter(name = "size", description = "Page size", example = "10")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Expenses retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExpenseResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid date format")
            }
    )
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

    @Operation(
            summary = "Get all expenses",
            description = "Retrieve a paginated list of all expenses",
            parameters = {
                    @Parameter(name = "sortBy", description = "Sort field", example = "expenseDate"),
                    @Parameter(name = "page", description = "Page number", example = "1"),
                    @Parameter(name = "size", description = "Page size", example = "10")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Expenses retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExpenseResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid parameters")
            }
    )
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

    @Operation(
            summary = "Update an expense",
            description = "Update the details of an existing expense",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Expense updated successfully",
                            content = @Content(schema = @Schema(implementation = ExpenseResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Expense not found")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> updateExpense(@PathVariable String id,@RequestBody ExpenseRequest request) {
        ExpenseResponse expenseResponse = expenseService.updateExpense(request, id);
        return ResponseUtil.buildResponse(HttpStatus.OK, SUCCESS_UPDATE_EXPENSE, expenseResponse);
    }

    @Operation(
            summary = "Delete an expense",
            description = "Delete an expense by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Expense deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Expense not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable String id) {
        ExpenseResponse expenseResponse = expenseService.deleteExpense(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, SUCCESS_DELETE_EXPENSE, null);
    }
}

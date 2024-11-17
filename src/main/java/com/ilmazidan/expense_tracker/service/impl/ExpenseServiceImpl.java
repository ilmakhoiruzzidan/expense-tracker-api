package com.ilmazidan.expense_tracker.service.impl;

import com.ilmazidan.expense_tracker.dto.request.ExpenseRequest;
import com.ilmazidan.expense_tracker.dto.request.PagingAndSortRequest;
import com.ilmazidan.expense_tracker.dto.response.ExpenseResponse;
import com.ilmazidan.expense_tracker.entity.Category;
import com.ilmazidan.expense_tracker.entity.Expense;
import com.ilmazidan.expense_tracker.entity.UserAccount;
import com.ilmazidan.expense_tracker.repository.ExpenseRepository;
import com.ilmazidan.expense_tracker.service.CategoryService;
import com.ilmazidan.expense_tracker.service.ExpenseService;
import com.ilmazidan.expense_tracker.util.Mapper;
import com.ilmazidan.expense_tracker.util.SortUtil;
import com.ilmazidan.expense_tracker.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;

    private final CategoryService categoryService;
    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ExpenseResponse createExpense(ExpenseRequest request) {
        validationUtil.validate(request);
        Category category = categoryService.getOne(request.getCategoryId());
        String currentUser = getCurrentUser().getUsername();
        UserAccount userAccount = getCurrentUser();
        Expense expense = Expense.builder()
                .id(UUID.randomUUID().toString())
                .expenseDate(request.getExpenseDate())
                .category(category)
                .userAccount(userAccount)
                .amountPaid(request.getAmountPaid())
                .description(request.getDescription())
                .createdBy(currentUser)
                .lastModifiedBy(currentUser)
                .lastModifiedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();
        expenseRepository.saveExpense(expense);
        return Mapper.toExpenseResponse(expense);
    }

    @Transactional(readOnly = true)
    @Override
    public ExpenseResponse getExpenseById(String id) {
        Expense expense = getOne(id);
        return Mapper.toExpenseResponse(expense);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ExpenseResponse> getAllExpenses(PagingAndSortRequest request) {
        Sort sortBy = SortUtil.parseSort(request.getSortBy());
        Pageable pageRequest = PageRequest.of(request.getPage(), request.getSize(), sortBy);
        Page<Expense> expenses = expenseRepository.findAllExpenses(pageRequest);
        return expenses.map(Mapper::toExpenseResponse);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ExpenseResponse updateExpense(ExpenseRequest request, String id) {
        Expense expense = getOne(id);
        Category newCategory = categoryService.getOne(request.getCategoryId());
        Expense newExpense = Expense.builder()
                .id(expense.getId())
                .expenseDate(request.getExpenseDate())
                .amountPaid(request.getAmountPaid())
                .description(request.getDescription())
                .category(newCategory)
                .build();
        expenseRepository.updateExpense(newExpense);
        return Mapper.toExpenseResponse(newExpense);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ExpenseResponse deleteExpense(String id) {
        Expense expense = getOne(id);
        expenseRepository.deleteExpense(expense.getId());
        return Mapper.toExpenseResponse(expense);
    }


    @Override
    public Expense getOne(String id) {
        return expenseRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found")
        );
    }

    public UserAccount getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserAccount) authentication.getPrincipal();
    }


}

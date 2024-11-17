package com.ilmazidan.expense_tracker.service.impl;

import com.ilmazidan.expense_tracker.dto.request.ExpensePaymentRequest;
import com.ilmazidan.expense_tracker.dto.request.PagingAndSortRequest;
import com.ilmazidan.expense_tracker.dto.response.ExpensePaymentResponse;
import com.ilmazidan.expense_tracker.entity.Expense;
import com.ilmazidan.expense_tracker.entity.ExpensePayment;
import com.ilmazidan.expense_tracker.entity.PaymentMethod;
import com.ilmazidan.expense_tracker.entity.UserAccount;
import com.ilmazidan.expense_tracker.repository.ExpensePaymentRepository;
import com.ilmazidan.expense_tracker.service.ExpensePaymentService;
import com.ilmazidan.expense_tracker.service.ExpenseService;
import com.ilmazidan.expense_tracker.service.PaymentMethodService;
import com.ilmazidan.expense_tracker.util.Mapper;
import com.ilmazidan.expense_tracker.util.SortUtil;
import com.ilmazidan.expense_tracker.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpensePaymentServiceImpl implements ExpensePaymentService {
    private final ExpensePaymentRepository expensePaymentRepository;

    private final PaymentMethodService paymentMethodService;
    private final ExpenseService expenseService;
    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ExpensePaymentResponse createExpensePayment(ExpensePaymentRequest request) {
        validationUtil.validate(request);
        Expense expense = expenseService.getOne(request.getExpenseId());
        PaymentMethod paymentMethod = paymentMethodService.getOne(request.getPaymentId());
        String currentUser = getCurrentUser();
        ExpensePayment expensePayment = ExpensePayment.builder()
                .id(UUID.randomUUID().toString())
                .expense(expense)
                .createdAt(LocalDateTime.now())
                .createdBy(currentUser)
                .lastModifiedBy(currentUser)
                .lastModifiedAt(LocalDateTime.now())
                .paymentMethod(paymentMethod)
                .build();
        expensePaymentRepository.saveExpensePayment(expensePayment);
        return Mapper.toExpensePaymentResponse(expensePayment);
    }

    @Transactional(readOnly = true)
    @Override
    public ExpensePaymentResponse getExpensePaymentById(String id) {
        ExpensePayment expensePayment = getOne(id);
        return Mapper.toExpensePaymentResponse(expensePayment);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ExpensePaymentResponse> getAllExpensePayments(PagingAndSortRequest request) {
        Sort sort = SortUtil.parseSort(request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);
        Page<ExpensePayment> expensePaymentPage = expensePaymentRepository.findAll(pageable);
        return expensePaymentPage.map(Mapper::toExpensePaymentResponse);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ExpensePaymentResponse updateExpensePayment(ExpensePaymentRequest request, String id) {
        ExpensePayment expensePayment = getOne(id);
        Expense expense = expenseService.getOne(request.getExpenseId());
        PaymentMethod paymentMethod = paymentMethodService.getOne(request.getPaymentId());
        String currentUser = getCurrentUser();
        ExpensePayment newExpensePayment = ExpensePayment.builder()
                .id(expensePayment.getId())
                .expense(expense)
                .lastModifiedAt(LocalDateTime.now())
                .lastModifiedBy(currentUser)
                .paymentMethod(paymentMethod)
                .build();
        expensePaymentRepository.updateExpensePaymentById(newExpensePayment);
        return Mapper.toExpensePaymentResponse(newExpensePayment);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteExpensePaymentById(String id) {
        ExpensePayment expensePayment = getOne(id);
        expensePaymentRepository.deleteExpensePaymentById(expensePayment.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public ExpensePayment getOne(String id) {
        return expensePaymentRepository.findExpenseById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense Payment not found")
        );
    }

    public String getCurrentUser() {
        UserAccount userAccount = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userAccount.getUsername();
    }
}

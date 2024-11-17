package com.ilmazidan.expense_tracker.util;

import com.ilmazidan.expense_tracker.constant.PaymentMethodType;
import com.ilmazidan.expense_tracker.dto.response.*;
import com.ilmazidan.expense_tracker.entity.*;

public class Mapper {
    public static UserResponse toUserResponse(UserAccount userAccount) {
        return UserResponse.builder()
                .id(userAccount.getId())
                .username(userAccount.getUsername())
                .role(userAccount.getRole().getDescription())
                .build();
    }

    public static RegisterResponse toRegisterResponse(UserAccount userAccount) {
        return RegisterResponse.builder()
                .id(userAccount.getId())
                .username(userAccount.getUsername())
                .role(userAccount.getRole().getDescription())
                .build();
    }

    public static CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .categoryName(category.getCategory().getDescription())
                .description(category.getDescription())
                .build();
    }

    public static PaymentMethodResponse toPaymentMethodResponse(PaymentMethod paymentMethod) {
        return PaymentMethodResponse.builder()
                .id(paymentMethod.getId())
                .paymentMethodType(paymentMethod.getPaymentMethodType().getDescription())
                .description(paymentMethod.getDescription())
                .build();
    }

    public static ExpensePaymentResponse toExpensePaymentResponse(ExpensePayment expensePayment) {
        return ExpensePaymentResponse.builder()
                .id(expensePayment.getId())
                .expenseId(expensePayment.getExpense().getId())
                .paymentId(expensePayment.getPaymentMethod().getId())
                .build();
    }

    public static ExpenseResponse toExpenseResponse(Expense expense) {
        return ExpenseResponse.builder()
                .id(expense.getId())
                .amountPaid(expense.getAmountPaid())
                .categoryId(expense.getCategory().getId())
                .expenseDate(expense.getExpenseDate())
                .description(expense.getDescription())
                .userId(expense.getUserAccount().getId())
                .build();
    }

}

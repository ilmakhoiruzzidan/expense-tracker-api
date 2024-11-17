package com.ilmazidan.expense_tracker.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpensePaymentRequest {
    private String expenseId;
    private String paymentId;
}

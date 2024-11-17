package com.ilmazidan.expense_tracker.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpensePaymentResponse {
    private String id;
    private String expenseId;
    private String paymentId;
}

package com.ilmazidan.expense_tracker.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseResponse {
    private String id;
    private Long amountPaid;
    private String description;
    private LocalDateTime expenseDate;
    private String categoryId;
    private String userId;
}

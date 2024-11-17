package com.ilmazidan.expense_tracker.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseRequest {

    @Schema(example = "20000")
    private Long amountPaid;

    @Schema(example = "Makan di warung")
    private String description;

    @Schema(example = "2024-09-08")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expenseDate;

    private String categoryId;
}

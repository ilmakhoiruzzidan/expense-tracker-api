package com.ilmazidan.expense_tracker.dto.request;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequest {
    private String description;
    private String categoryName;
}

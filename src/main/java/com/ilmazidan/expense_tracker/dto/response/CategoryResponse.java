package com.ilmazidan.expense_tracker.dto.response;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {
    private String id;
    private String description;
    private String categoryName;
}

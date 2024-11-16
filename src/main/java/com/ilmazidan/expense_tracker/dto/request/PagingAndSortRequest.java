package com.ilmazidan.expense_tracker.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagingAndSortRequest {
    private Integer page;
    private Integer size;
    private String sortBy;

    public Integer getPage() {
        return page <= 0 ? 0 : page - 1;
    }
}

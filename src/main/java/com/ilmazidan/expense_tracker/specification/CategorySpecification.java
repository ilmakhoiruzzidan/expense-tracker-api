package com.ilmazidan.expense_tracker.specification;

import com.ilmazidan.expense_tracker.dto.request.PagingAndSortRequest;
import com.ilmazidan.expense_tracker.entity.Category;
import org.springframework.data.jpa.domain.Specification;

public class CategorySpecification {
    public static Specification<Category> getSpecification(PagingAndSortRequest request) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }
}

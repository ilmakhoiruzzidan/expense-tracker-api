package com.ilmazidan.expense_tracker.spesification;

import com.ilmazidan.expense_tracker.dto.request.PagingAndSortRequest;
import com.ilmazidan.expense_tracker.entity.Category;
import org.springframework.data.jpa.domain.Specification;

public class PaymentMethodSpecification {
    public static Specification<Category> getSpecification(PagingAndSortRequest request) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }
}
package com.ilmazidan.expense_tracker.specification;

import com.ilmazidan.expense_tracker.dto.request.PagingAndSortRequest;
import com.ilmazidan.expense_tracker.entity.Category;
import com.ilmazidan.expense_tracker.entity.PaymentMethod;
import org.springframework.data.jpa.domain.Specification;

public class PaymentMethodSpecification {
    public static Specification<PaymentMethod> getSpecification(PagingAndSortRequest request) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }
}

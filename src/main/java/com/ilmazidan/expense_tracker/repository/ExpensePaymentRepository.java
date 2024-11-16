package com.ilmazidan.expense_tracker.repository;

import com.ilmazidan.expense_tracker.entity.ExpensePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensePaymentRepository extends JpaRepository<ExpensePayment, String> {
}

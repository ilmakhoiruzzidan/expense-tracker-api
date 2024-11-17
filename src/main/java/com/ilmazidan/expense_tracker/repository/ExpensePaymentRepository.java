package com.ilmazidan.expense_tracker.repository;

import com.ilmazidan.expense_tracker.entity.ExpensePayment;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ExpensePaymentRepository extends JpaRepository<ExpensePayment, String> {
    @Transactional
    @Modifying
    @Query(
            nativeQuery = true,
            value = "INSERT INTO t_expense_payment (" +
                    "id, expense_id, payment_method_id, created_by, created_at, last_modified_by, last_modified_at) " +
                    "VALUES (" +
                    ":#{#expensePayment.id}, " +
                    ":#{#expensePayment.expense.id}, " +
                    ":#{#expensePayment.paymentMethod.id}, " +
                    ":#{#expensePayment.createdBy}, " +
                    ":#{#expensePayment.createdAt}, " +
                    ":#{#expensePayment.lastModifiedBy}, " +
                    ":#{#expensePayment.lastModifiedAt})"
    )
    void saveExpensePayment(ExpensePayment expensePayment);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM t_expense_payment WHERE id = :id"
    )
    Optional<ExpensePayment> findExpenseById(String id);


    @Transactional
    @Modifying
    @Query(
            nativeQuery = true,
            value = "DELETE FROM t_expense_payment WHERE id = :id"
    )
    void deleteExpensePaymentById(String id);

    @NonNull
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM t_expense_payment"
    )
    Page<ExpensePayment> findAll(@NonNull Pageable pageable);

    @Transactional
    @Modifying
    @Query(
            nativeQuery = true,
            value = "UPDATE t_expense_payment " +
                    "SET expense_id = :#{#expensePayment.expense.id}, " +
                    "payment_method_id = :#{#expensePayment.paymentMethod.id}, " +
                    "last_modified_by = :#{#expensePayment.lastModifiedBy}, " +
                    "last_modified_at = CURRENT_TIMESTAMP " +
                    "WHERE id = :#{#expensePayment.id}"
    )
    void updateExpensePaymentById(ExpensePayment expensePayment);
}

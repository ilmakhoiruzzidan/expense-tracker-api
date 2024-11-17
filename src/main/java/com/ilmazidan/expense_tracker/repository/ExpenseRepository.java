package com.ilmazidan.expense_tracker.repository;

import com.ilmazidan.expense_tracker.entity.Expense;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, String> {
    @Transactional
    @Modifying
    @Query(
            nativeQuery = true,
            value = "INSERT INTO t_expense (" +
                    "id, created_at, created_by, last_modified_at, last_modified_by, amount_paid, " +
                    "description, expense_date, category_id, user_account_id) " +
                    "VALUES (" +
                    ":#{#expense.id}, CURRENT_TIMESTAMP, :#{#expense.createdBy}, CURRENT_TIMESTAMP, :#{#expense.lastModifiedBy}, " +
                    ":#{#expense.amountPaid}, :#{#expense.description}, :#{#expense.expenseDate}, " +
                    "(SELECT id FROM m_category WHERE id = :#{#expense.category.id}), " +
                    "(SELECT id FROM m_user_account WHERE id = :#{#expense.userAccount.id}))"
    )
    void saveExpense(Expense expense);

    @NonNull
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM t_expense WHERE id = :id"
    )
    Optional<Expense> findById(@NonNull String id);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM t_expense " +
                    "WHERE expense_date BETWEEN :startDate AND :endDate"
    )
    Page<Expense> findExpenseByDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @NonNull Pageable pageable
    );

    @Transactional
    @Modifying
    @Query(
            nativeQuery = true,
            value = "UPDATE t_expense SET " +
                    "amount_paid = :#{#expense.amountPaid}, " +
                    "description = :#{#expense.description}, " +
                    "expense_date = :#{#expense.expenseDate}, " +
                    "category_id = (SELECT id FROM m_category WHERE id = :#{#expense.category.id}), " +
                    "user_account_id = (SELECT id FROM m_user_account WHERE id = :#{#expense.userAccount.id}), " +
                    "last_modified_at = CURRENT_TIMESTAMP, " +
                    "last_modified_by = :#{#expense.lastModifiedBy} " +
                    "WHERE id = :#{#expense.id}"
    )
    void updateExpense(Expense expense);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "DELETE FROM t_expense WHERE id = :id"
    )
    void deleteExpense(@NonNull String id);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM t_expense"
    )
    Page<Expense> findAllExpenses(Pageable pageable);
}

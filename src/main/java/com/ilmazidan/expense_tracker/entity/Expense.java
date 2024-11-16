package com.ilmazidan.expense_tracker.entity;

import com.ilmazidan.expense_tracker.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "t_expense")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Expense extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "amount_paid", nullable = false, columnDefinition = "bigint check (amount_paid > 0) ")
    private Long amountPaid;

    @Column(name = "expense_date", nullable = false)
    private LocalDateTime expenseDate;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "expense")
    private List<ExpensePayment> expensePayment;

    @ManyToOne
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}

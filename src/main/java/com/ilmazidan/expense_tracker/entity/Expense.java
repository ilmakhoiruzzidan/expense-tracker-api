package com.ilmazidan.expense_tracker.entity;

import com.ilmazidan.expense_tracker.constant.Constant;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = Constant.EXPENSE_TABLE)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "amount_paid", nullable = false, columnDefinition = "bigint check (amount_paid > 0) ")
    private Long amountPaid;

    @Temporal(TemporalType.TIMESTAMP)
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

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_at")
    private LocalDateTime lastModifiedAt;
}

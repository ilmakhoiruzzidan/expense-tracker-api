package com.ilmazidan.expense_tracker.entity;

import com.ilmazidan.expense_tracker.constant.Constant;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = Constant.EXPENSE_PAYMENT)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpensePayment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    private Expense expense;

    @ManyToOne
    private PaymentMethod paymentMethod;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_at")
    private LocalDateTime lastModifiedAt;
}

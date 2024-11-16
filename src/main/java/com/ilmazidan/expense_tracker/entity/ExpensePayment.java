package com.ilmazidan.expense_tracker.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_expense_payment")
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
}

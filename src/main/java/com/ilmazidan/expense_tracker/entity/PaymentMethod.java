package com.ilmazidan.expense_tracker.entity;

import com.ilmazidan.expense_tracker.constant.PaymentMethodType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "m_payment_method")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "payment_method", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethodType paymentMethod;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "paymentMethod")
    private List<ExpensePayment> expensePayment;

}

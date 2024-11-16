package com.ilmazidan.expense_tracker.entity;

import com.ilmazidan.expense_tracker.constant.ExpenseCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "m_category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private ExpenseCategory category;

    @OneToMany(mappedBy = "category")
    private List<Expense> expenses;
}

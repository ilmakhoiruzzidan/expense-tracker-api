package com.ilmazidan.expense_tracker.constant;

import lombok.Getter;

@Getter
public enum ExpenseCategory {
    EDUCATION("Education"),
    ENTERTAINMENT("Entertainment"),
    FOOD("Food"),
    TRANSPORTATION("Transportation");

    private final String description;

    ExpenseCategory(String description) {this.description = description;}


    public static ExpenseCategory findByDescription(String name) {
        for (ExpenseCategory value : values()) {
            if (value.description.equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }
}

package com.ilmazidan.expense_tracker.constant;

import lombok.Getter;

@Getter
public enum PaymentMethodType {
    CREDIT_CARD("Credit Card"),
    BANK_TRANSFER("Bank Transfer"),
    CASH("Cash"),
    QRIS("Qris"),;

    private final String description;

    PaymentMethodType(String description) {this.description = description;}


    public static PaymentMethodType findByDescription(String name) {
        for (PaymentMethodType value : values()) {
            if (value.description.equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }
}

package com.ilmazidan.expense_tracker.constant;

import lombok.Getter;

@Getter
public enum UserRole {
    ROLE_ADMIN("Admin"),
    ROLE_USER("User");

    private final String description;

    UserRole(String description) {this.description = description;}


    public static UserRole findByDescription(String name) {
        for (UserRole value : values()) {
            if (value.description.equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }
}

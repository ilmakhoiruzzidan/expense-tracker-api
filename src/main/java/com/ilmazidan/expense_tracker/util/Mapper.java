package com.ilmazidan.expense_tracker.util;

import com.ilmazidan.expense_tracker.dto.response.CategoryResponse;
import com.ilmazidan.expense_tracker.dto.response.RegisterResponse;
import com.ilmazidan.expense_tracker.dto.response.UserResponse;
import com.ilmazidan.expense_tracker.entity.Category;
import com.ilmazidan.expense_tracker.entity.UserAccount;

public class Mapper {
    public static UserResponse toUserResponse(UserAccount userAccount) {
        return UserResponse.builder()
                .id(userAccount.getId())
                .username(userAccount.getUsername())
                .role(userAccount.getRole().getDescription())
                .build();
    }
    public static RegisterResponse toRegisterResponse(UserAccount userAccount) {
        return RegisterResponse.builder()
                .id(userAccount.getId())
                .username(userAccount.getUsername())
                .role(userAccount.getRole().getDescription())
                .build();
    }

    public static CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .categoryName(category.getCategory().getDescription())
                .description(category.getDescription())
                .build();
    }
}

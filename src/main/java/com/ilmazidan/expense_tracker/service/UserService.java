package com.ilmazidan.expense_tracker.service;

import com.ilmazidan.expense_tracker.dto.request.UserRequest;
import com.ilmazidan.expense_tracker.dto.response.UserResponse;
import com.ilmazidan.expense_tracker.entity.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserResponse create(UserRequest request);
    UserAccount create(UserAccount userAccount);
    UserAccount getById(String id);
}

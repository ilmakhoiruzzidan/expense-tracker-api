package com.ilmazidan.expense_tracker.service;

import com.ilmazidan.expense_tracker.dto.request.AuthRequest;
import com.ilmazidan.expense_tracker.dto.request.RegisterRequest;
import com.ilmazidan.expense_tracker.dto.response.AuthResponse;
import com.ilmazidan.expense_tracker.dto.response.RegisterResponse;

public interface AuthService {
    AuthResponse login(AuthRequest request);
    RegisterResponse register(RegisterRequest request);
    AuthResponse refreshToken(String token);
    void logout(String accessToken);

}

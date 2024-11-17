package com.ilmazidan.expense_tracker.service;

import com.ilmazidan.expense_tracker.entity.UserAccount;
import jakarta.servlet.http.HttpServletRequest;

public interface JwtService {
    String generateAccessToken(UserAccount userAccount);
    String getUserId(String token);

    String extractTokenFromRequest(HttpServletRequest request);
    void blacklistAccessToken(String accessToken);
}

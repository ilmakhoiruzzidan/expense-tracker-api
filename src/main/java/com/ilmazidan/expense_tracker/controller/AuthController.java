package com.ilmazidan.expense_tracker.controller;

import com.ilmazidan.expense_tracker.constant.Constant;
import com.ilmazidan.expense_tracker.dto.request.AuthRequest;
import com.ilmazidan.expense_tracker.dto.request.RegisterRequest;
import com.ilmazidan.expense_tracker.dto.response.AuthResponse;
import com.ilmazidan.expense_tracker.dto.response.RegisterResponse;
import com.ilmazidan.expense_tracker.service.AuthService;
import com.ilmazidan.expense_tracker.util.ResponseUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;



@RequestMapping(Constant.AUTH_API)
@RestController
@RequiredArgsConstructor
public class AuthController {
    @Value("${expense-tracker.refresh-token-expiration-in-hour}")
    private Integer REFRESH_TOKEN_EXPIRY;

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login (
            @RequestBody AuthRequest authRequest,
            HttpServletResponse response
    ) {
        AuthResponse authResponse = authService.login(authRequest);
        setCookie(response, authResponse.getRefreshToken());
        return ResponseUtil.buildResponse(HttpStatus.OK, Constant.SUCCESS_LOGIN, authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register (
            @RequestBody RegisterRequest registerRequest
    ) {
        RegisterResponse registerResponse = authService.register(registerRequest);
        return ResponseUtil.buildResponse(HttpStatus.OK, Constant.SUCCESS_REGISTER, registerResponse);
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken (
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String refreshToken = getRefreshTokenFromCookie(request);
        AuthResponse authResponse = authService.refreshToken(refreshToken);
        setCookie(response, authResponse.getRefreshToken());
        return ResponseUtil.buildResponse(HttpStatus.OK, Constant.SUCCESS_REFRESH_TOKEN, authResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout (
            HttpServletRequest request
    ) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        authService.logout(bearerToken);
        return ResponseUtil.buildResponse(HttpStatus.OK, Constant.SUCCESS_LOGOUT, null);
    }

    private String getRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie cookie = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(Constant.REFRESH_TOKEN))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, Constant.REQUIRE_REFRESH_TOKEN));
        return cookie.getValue();
    }

    private void setCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie(Constant.REFRESH_TOKEN, refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * REFRESH_TOKEN_EXPIRY);
        response.addCookie(cookie);
    }
}

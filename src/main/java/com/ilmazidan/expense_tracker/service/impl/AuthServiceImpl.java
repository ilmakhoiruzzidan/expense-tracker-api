package com.ilmazidan.expense_tracker.service.impl;

import com.ilmazidan.expense_tracker.constant.UserRole;
import com.ilmazidan.expense_tracker.dto.request.AuthRequest;
import com.ilmazidan.expense_tracker.dto.request.RegisterRequest;
import com.ilmazidan.expense_tracker.dto.response.AuthResponse;
import com.ilmazidan.expense_tracker.dto.response.RegisterResponse;
import com.ilmazidan.expense_tracker.entity.UserAccount;
import com.ilmazidan.expense_tracker.service.AuthService;
import com.ilmazidan.expense_tracker.service.JwtService;
import com.ilmazidan.expense_tracker.service.RefreshTokenService;
import com.ilmazidan.expense_tracker.service.UserService;
import com.ilmazidan.expense_tracker.util.Mapper;
import com.ilmazidan.expense_tracker.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final UserService userService;

    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final ValidationUtil validationUtil;

    @Transactional(readOnly = true)
    @Override
    public AuthResponse login(AuthRequest request) {
        validationUtil.validate(request);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserAccount userAccount = (UserAccount) authentication.getPrincipal();
        String refreshToken = refreshTokenService.createToken(userAccount.getId());
        String accessToken = jwtService.generateAccessToken(userAccount);
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .role(userAccount.getRole().getDescription())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse register(RegisterRequest request) {
        try {
            validationUtil.validate(request);
            if (request.getRole().equalsIgnoreCase("ADMIN")) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error role is not found");
            } else if (request.getRole().equalsIgnoreCase("USER")) {
                UserAccount userAccount = UserAccount.builder()
                        .id(UUID.randomUUID().toString())
                        .username(request.getUsername())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(UserRole.ROLE_USER)
                        .build();
                userService.create(userAccount);
                return Mapper.toRegisterResponse(userAccount);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid role");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public AuthResponse refreshToken(String token) {
        String userId = refreshTokenService.getUserIdByToken(token);
        UserAccount userAccount = userService.getById(userId);
        String newRefreshToken = refreshTokenService.rotateRefreshToken(userId);
        String newToken = jwtService.generateAccessToken(userAccount);
        return AuthResponse.builder()
                .accessToken(newToken)
                .refreshToken(newRefreshToken)
                .role(userAccount.getRole().getDescription())
                .build();
    }

    @Override
    public void logout(String accessToken) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userAccount = (UserAccount) authentication.getPrincipal();
        refreshTokenService.deleteRefreshToken(userAccount.getId());
        jwtService.blacklistAccessToken(accessToken);
    }
}

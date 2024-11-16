package com.ilmazidan.expense_tracker.service.impl;

import com.ilmazidan.expense_tracker.constant.UserRole;
import com.ilmazidan.expense_tracker.dto.request.UserRequest;
import com.ilmazidan.expense_tracker.dto.response.UserResponse;
import com.ilmazidan.expense_tracker.entity.UserAccount;
import com.ilmazidan.expense_tracker.repository.UserAccountRepository;
import com.ilmazidan.expense_tracker.service.UserService;
import com.ilmazidan.expense_tracker.util.Mapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Value("${expense-tracker.user-admin}")
    private String USERNAME_ADMIN;
    @Value("${expense-tracker.user-password}")
    private String PASSWORD_ADMIN;

    private final PasswordEncoder passwordEncoder;
    private final UserAccountRepository userAccountRepository;

    @PostConstruct
    public void initUser() {
        boolean isExist = userAccountRepository.existsByUsername(USERNAME_ADMIN);
        if (isExist) return;
        try {
            UserAccount userAccount = UserAccount.builder()
                    .id(UUID.randomUUID().toString())
                    .username(USERNAME_ADMIN)
                    .password(passwordEncoder.encode(PASSWORD_ADMIN))
                    .role(UserRole.ROLE_ADMIN)
                    .build();

            userAccountRepository.saveUserAccount(userAccount);
        } catch (Exception e) {
            log.error("Error initializing user: " + e.getMessage(), e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse create(UserRequest request) {
        try {
            UserRole userRole = UserRole.findByDescription(request.getRole());
            if (userRole == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error role not found");
            UserAccount userAccount = UserAccount.builder()
                    .id(UUID.randomUUID().toString())
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(userRole)
                    .build();
            userAccountRepository.saveUserAccount(userAccount);
            return Mapper.toUserResponse(userAccount);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserAccount create(UserAccount userAccount) {
        return userAccountRepository.save(userAccount);
    }

    @Transactional(readOnly = true)
    @Override
    public UserAccount getById(String id) {
        return userAccountRepository.findUserById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAccountRepository.findByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }
}

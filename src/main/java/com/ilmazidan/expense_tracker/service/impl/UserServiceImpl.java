package com.ilmazidan.expense_tracker.service.impl;

import com.ilmazidan.expense_tracker.constant.Constant;
import com.ilmazidan.expense_tracker.constant.UserRole;
import com.ilmazidan.expense_tracker.dto.request.UserRequest;
import com.ilmazidan.expense_tracker.dto.request.UserUpdatePasswordRequest;
import com.ilmazidan.expense_tracker.dto.response.UserResponse;
import com.ilmazidan.expense_tracker.entity.UserAccount;
import com.ilmazidan.expense_tracker.repository.UserAccountRepository;
import com.ilmazidan.expense_tracker.service.UserService;
import com.ilmazidan.expense_tracker.util.Mapper;
import com.ilmazidan.expense_tracker.util.ValidationUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final ValidationUtil validationUtil;

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
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ERROR_ROLE_NOT_FOUND);
            UserAccount userAccount = UserAccount.builder()
                    .id(UUID.randomUUID().toString())
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(userRole)
                    .build();
            userAccountRepository.saveUserAccount(userAccount);
            return Mapper.toUserResponse(userAccount);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, Constant.ERROR_USER_ALREADY_EXISTS);
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userAccount = (UserAccount) authentication.getPrincipal();
        return Mapper.toUserResponse(userAccount);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updatePassword(String id, UserUpdatePasswordRequest request) {
        validationUtil.validate(request);
        UserAccount userAccount = getById(id);

        if (!passwordEncoder.matches(request.getCurrentPassword(), userAccount.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constant.ERROR_INVALID_CREDENTIALS);
        }

        String encodedNewPassword = passwordEncoder.encode(request.getNewPassword());
        userAccountRepository.updatePassword(id, encodedNewPassword);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAccountRepository.findByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ERROR_USER_NOT_FOUND)
        );
    }
}

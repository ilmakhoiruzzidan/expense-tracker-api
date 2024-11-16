package com.ilmazidan.expense_tracker.controller;

import com.ilmazidan.expense_tracker.dto.request.UserRequest;
import com.ilmazidan.expense_tracker.dto.response.UserResponse;
import com.ilmazidan.expense_tracker.service.UserService;
import com.ilmazidan.expense_tracker.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserRequest request) {
        UserResponse userResponse = userService.create(request);
        return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully create user", userResponse);
    }
}
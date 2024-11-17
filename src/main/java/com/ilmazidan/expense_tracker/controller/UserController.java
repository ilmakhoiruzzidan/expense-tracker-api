package com.ilmazidan.expense_tracker.controller;

import com.ilmazidan.expense_tracker.constant.Constant;
import com.ilmazidan.expense_tracker.dto.request.UserRequest;
import com.ilmazidan.expense_tracker.dto.request.UserUpdatePasswordRequest;
import com.ilmazidan.expense_tracker.dto.response.UserResponse;
import com.ilmazidan.expense_tracker.service.UserService;
import com.ilmazidan.expense_tracker.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.ilmazidan.expense_tracker.constant.Constant.*;

@RestController
@RequestMapping(Constant.USERS_API)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserRequest request) {
        UserResponse userResponse = userService.create(request);
        return ResponseUtil.buildResponse(HttpStatus.OK, SUCCESS_CREATE_USER, userResponse);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUserInfo() {
        UserResponse userResponse = userService.getAuthentication();
        return ResponseUtil.buildResponse(HttpStatus.OK, SUCCESS_GET_CURRENT_USER_INFO, userResponse);
    }

    @PatchMapping("/{id}/update-password")
    public ResponseEntity<?> updatePassword(@PathVariable String id, @RequestBody UserUpdatePasswordRequest request) {
        userService.updatePassword(id, request);
        return ResponseUtil.buildResponse(HttpStatus.OK, SUCCESS_UPDATE_USER_PASSWORD, null);
    }

}

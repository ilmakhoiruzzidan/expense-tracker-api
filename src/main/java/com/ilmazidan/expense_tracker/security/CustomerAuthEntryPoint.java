package com.ilmazidan.expense_tracker.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilmazidan.expense_tracker.dto.response.CommonResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomerAuthEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        CommonResponse<?> commonResponse = new CommonResponse<>(
                HttpStatus.UNAUTHORIZED.value(),
                authException.getMessage(),
                null,
                null
        );
        String stringCommonResponse = objectMapper.writeValueAsString(commonResponse);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(stringCommonResponse);
    }

}

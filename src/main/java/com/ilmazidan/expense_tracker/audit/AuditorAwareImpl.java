package com.ilmazidan.expense_tracker.audit;

import com.ilmazidan.expense_tracker.constant.Constant;
import com.ilmazidan.expense_tracker.entity.UserAccount;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Slf4j
public class AuditorAwareImpl implements AuditorAware<String> {
    @NonNull
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Current user: {}", authentication);
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            // return a default value or handle cases where no user is authenticated will return as SYSTEM
            return Optional.of(Constant.SYSTEM);
        }

        UserAccount userAccount = (UserAccount) authentication.getPrincipal();
        return Optional.of(userAccount.getUsername().toUpperCase());
    }
}

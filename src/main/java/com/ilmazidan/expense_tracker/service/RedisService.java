package com.ilmazidan.expense_tracker.service;

import java.time.Duration;

public interface RedisService {
    void save(String key, String value, Duration duration);
    String get(String key);
    void delete(String key);
}

package com.example.demo.domain;

import jakarta.annotation.Nullable;

public record User(Integer userId, String phoneNumber, String name, @Nullable String password, @Nullable String hashedPassword) {
}

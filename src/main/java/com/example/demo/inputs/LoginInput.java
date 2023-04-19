package com.example.demo.inputs;

/**
 * We need phone number and password to login.
 */
public record LoginInput(
        String phoneNumber,
        String password
) {
}

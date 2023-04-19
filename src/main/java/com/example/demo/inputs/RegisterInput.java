package com.example.demo.inputs;

import com.example.demo.Constants;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Registeration has more rules and we use Aspect Oriented Programming to fulfill the requirement.
 * Because regex is very tricky, we need to thoroughly test this.
 * TODO test this!
 */
public record RegisterInput(
        @Size(min = 10, max = 13)
        @Pattern(regexp = Constants.INDONESIAN_PHONE_REGEX)
        String phoneNumber,
        @NotEmpty
        String name,
        @Size(min = 6, max = 16)
        @Pattern(regexp = Constants.ONE_DIGIT_REGEX) // at least 1 digit
        @Pattern(regexp = Constants.ONE_UPPERCASE_REGEX) // at least 1 capital letter
        String password
) {
}

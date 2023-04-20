package com.example.demo.domain;

import java.util.List;
import java.util.Optional;

public record ValidationFailed(List<ValidationError> errors) implements ValidationResult {

    public String getErrorString() {
        Optional<String> errorStr = errors.stream()
                .map(ValidationError::reason)
                .reduce((String accum, String element) -> {
                    return accum + ", " + element;
                });
        return errorStr.orElse("");
    }
}

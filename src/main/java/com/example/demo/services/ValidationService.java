package com.example.demo.services;

import com.example.demo.domain.ValidationError;
import com.example.demo.domain.ValidationFailed;
import com.example.demo.domain.ValidationResult;
import com.example.demo.domain.ValidationSuccess;
import com.example.demo.inputs.RegisterInput;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * I can't use Spring Boot validation somehow. So I just created a validation service.
 * TODO test this!
 */
@Service
public class ValidationService {
    public ValidationResult validateRegisterInput(RegisterInput registerInput) {
        List<ValidationError> errorList = new ArrayList<>();
        String phoneNumber = registerInput.phoneNumber();
        int phoneNumberLen = phoneNumber.length();
        if (phoneNumberLen < 10 || phoneNumberLen > 13)
            errorList.add(new ValidationError("phone number must have length from 10 to 13 (inclusive)"));
        if (!phoneNumber.startsWith("08"))
            errorList.add(new ValidationError("phone number must start with 08"));

        String name = registerInput.name();
        if (name == null || name.isEmpty())
            errorList.add(new ValidationError("name is required"));

        String password = registerInput.password();
        int passwordLen = password.length();
        if (passwordLen < 6 || passwordLen > 16)
            errorList.add(new ValidationError("phone number must have length from 6 to 16 (inclusive)"));

        boolean containDigits = false;
        for (int i = 0; i < passwordLen; i++) {
            char ch = password.charAt(i);
            if (Character.isDigit(ch))
                containDigits = true;
        }
        if (!containDigits)
            errorList.add(new ValidationError("password must contain at least 1 digit"));

        boolean containCapitals = false;
        for (int i = 0; i < passwordLen; i++) {
            char ch = password.charAt(i);
            if (Character.isUpperCase(ch))
                containCapitals = true;
        }

        if (!containCapitals)
            errorList.add(new ValidationError("password must contain at least 1 capital letter"));

        if (errorList.isEmpty()) {
            return new ValidationSuccess();
        } else {
            return new ValidationFailed(errorList);
        }
    }
}

package com.example.demo;

import com.example.demo.domain.ValidationFailed;
import com.example.demo.domain.ValidationResult;
import com.example.demo.domain.ValidationSuccess;
import com.example.demo.inputs.RegisterInput;
import com.example.demo.resources.AuthResource;
import com.example.demo.services.ValidationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

public class MainTests {

    ValidationService validationService = new ValidationService();

    @Test
    public void validationRegex_isCorrect() {
        String validPassword = "asdfA1";
        String validPhone = "081234567890";
        String validName = "Name1";

        ValidationResult validationResult;
        validationResult = validationService.validateRegisterInput(new RegisterInput("181234567890", validName, validPassword));
        assert validationResult instanceof ValidationFailed;
        validationResult = validationService.validateRegisterInput(new RegisterInput("091234567890", validName, validPassword));
        assert validationResult instanceof ValidationFailed;
        validationResult = validationService.validateRegisterInput(new RegisterInput("08123456789012", validName, validPassword));
        assert validationResult instanceof ValidationFailed;
        validationResult = validationService.validateRegisterInput(new RegisterInput("081234567", validName, validPassword));
        assert validationResult instanceof ValidationFailed;
        validationResult = validationService.validateRegisterInput(new RegisterInput(validPhone, validName, validPassword));
        assert validationResult instanceof ValidationSuccess;

        validationResult = validationService.validateRegisterInput(new RegisterInput(validPhone, validName, "asdf11"));
        assert validationResult instanceof ValidationFailed;
        validationResult = validationService.validateRegisterInput(new RegisterInput(validPhone, validName, "asdfAA"));
        assert validationResult instanceof ValidationFailed;
        validationResult = validationService.validateRegisterInput(new RegisterInput(validPhone, validName, "adfA1"));
        assert validationResult instanceof ValidationFailed;
        validationResult = validationService.validateRegisterInput(new RegisterInput(validPhone, validName, "abcdefghijklmnoA1"));
        assert validationResult instanceof ValidationFailed;
        // no need to check valid password case as it's the same as valid phone case
    }
}

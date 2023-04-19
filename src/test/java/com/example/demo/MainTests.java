package com.example.demo;

import com.example.demo.resources.AuthResource;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

public class MainTests {
    @Test
    public void validationRegex_isCorrect() {
        Pattern indonesianPhonePattern = Pattern.compile(Constants.INDONESIAN_PHONE_REGEX);
        assert indonesianPhonePattern.matcher("081234567890").matches();
        assert !indonesianPhonePattern.matcher("181234567890").matches();
        assert !indonesianPhonePattern.matcher("091234567890").matches();

        Pattern oneDigitPattern = Pattern.compile(Constants.ONE_DIGIT_REGEX);
        assert oneDigitPattern.matcher("asdf0asdf").matches();
        assert oneDigitPattern.matcher("0asdf").matches();
        assert oneDigitPattern.matcher("asdf0").matches();
        assert !oneDigitPattern.matcher("asdf").matches();

        Pattern oneCapitalPattern = Pattern.compile(Constants.ONE_UPPERCASE_REGEX);
        assert oneCapitalPattern.matcher("asdfAasdf").matches();
        assert oneCapitalPattern.matcher("Aasdf").matches();
        assert oneCapitalPattern.matcher("asdfA").matches();
        assert !oneCapitalPattern.matcher("asdf").matches();
    }
}

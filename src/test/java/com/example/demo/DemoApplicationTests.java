package com.example.demo;

import com.example.demo.repositories.UserRepository;
import com.example.demo.services.AuthService;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Most functionality in spring boot is tested in SpringBootTest, because we utilize a lot of Autowired annotation.
 */
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private AuthService authService;

    @MockBean
    private UserRepository userRepository;

    /**
     * register user should store stuff in database with hashedPassword
     */
    @Test
    public void userRegistration_working() {
        String password = "password1";

        authService.registerUser("081234567890", "Name 1", password);

        ArgumentCaptor<String> hashedPasswordCaptor = ArgumentCaptor.forClass(String.class);
        verify(userRepository).create(eq("081234567890"), eq("Name 1"), hashedPasswordCaptor.capture());

        assert BCrypt.checkpw(password, hashedPasswordCaptor.getValue());
    }

    /**
     * User login should find user by phone number and password.
     */
    @Test
    public void userLogin_working() {
        authService.validateUser("081234567890", "password1");
        verify(userRepository).findByPhoneAndPassword("081234567890", "password1");
    }
}

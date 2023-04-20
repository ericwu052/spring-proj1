package com.example.demo.resources;

import com.example.demo.Constants;
import com.example.demo.domain.User;
import com.example.demo.exceptions.MyAuthException;
import com.example.demo.exceptions.MyBadRequestException;
import com.example.demo.inputs.LoginInput;
import com.example.demo.inputs.RegisterInput;
import com.example.demo.outputs.MessageOutput;
import com.example.demo.outputs.TokenOutput;
import com.example.demo.repositories.KeyRepository;
import com.example.demo.services.AuthService;
import io.jsonwebtoken.Jwts;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

@RestController
@RequestMapping("/api/users")
public class AuthResource {

    @Autowired AuthService authService;
    @Autowired KeyRepository keyRepository;

    @PostMapping("/register")
    public ResponseEntity<MessageOutput> register(@Valid @RequestBody RegisterInput registerInput) throws MyBadRequestException {
        authService.registerUser(registerInput.phoneNumber, registerInput.name, registerInput.password);
        return new ResponseEntity<>(new MessageOutput("success"), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenOutput> login(@RequestBody LoginInput loginInput) throws MyAuthException {
        User user = authService.validateUser(loginInput.phoneNumber(), loginInput.password());
        return new ResponseEntity<>(new TokenOutput(generateJWTToken(user)), HttpStatus.OK);
    }

    private String generateJWTToken(User user) {
        long timestamp = System.currentTimeMillis();
        try {
            return Jwts.builder()
                    .setSubject(user.phoneNumber())
                    .setIssuedAt(new Date(timestamp))
                    .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
                    .signWith(keyRepository.getKeyPair().getPrivate())
                    .compact();
        } catch (NoSuchAlgorithmException | NoSuchProviderException | IOException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}

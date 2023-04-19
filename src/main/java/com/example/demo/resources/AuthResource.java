package com.example.demo.resources;

import com.example.demo.Constants;
import com.example.demo.domain.User;
import com.example.demo.exceptions.MyAuthException;
import com.example.demo.inputs.LoginInput;
import com.example.demo.inputs.RegisterInput;
import com.example.demo.outputs.MessageOutput;
import com.example.demo.outputs.TokenOutput;
import com.example.demo.services.AuthService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/users")
public class AuthResource {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<MessageOutput> register(@RequestBody RegisterInput registerInput) throws MyAuthException {
        authService.registerUser(registerInput.phoneNumber(), registerInput.name(), registerInput.password());
        return new ResponseEntity<>(new MessageOutput("success"), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenOutput> login(@RequestBody LoginInput loginInput) {
        User user = authService.validateUser(loginInput.phoneNumber(), loginInput.password());
        return new ResponseEntity<>(new TokenOutput(generateJWTToken(user)), HttpStatus.OK);
    }

    private String generateJWTToken(User user) {
        long timestamp = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(user.phoneNumber())
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
                .signWith(Constants.keyPair.getPrivate())
                .compact();
    }
}

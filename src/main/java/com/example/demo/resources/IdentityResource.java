package com.example.demo.resources;

import com.example.demo.domain.User;
import com.example.demo.exceptions.MyAuthException;
import com.example.demo.exceptions.MyBadRequestException;
import com.example.demo.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class IdentityResource {

    @Autowired
    AuthService authService;

    @GetMapping("/name")
    @Operation(
            security = {@SecurityRequirement(name = "BearerJWT")}
    )
    public ResponseEntity<Map<String, String>> getName(HttpServletRequest request) throws MyAuthException {
        String phone = (String) request.getAttribute("phoneNumber");
        User user = authService.getUserByPhone(phone);
        Map<String, String> map = new HashMap<>();
        map.put("name", user.name());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("/name")
    @Operation(
            security = {@SecurityRequirement(name = "BearerJWT")}
    )
    public ResponseEntity<Map<String, Boolean>> updateName(HttpServletRequest request, @RequestBody Map<String, Object> body) throws MyBadRequestException {
        String phone = (String) request.getAttribute("phoneNumber");
        String name = (String) body.get("name");
        authService.updateUserName(phone, name);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}

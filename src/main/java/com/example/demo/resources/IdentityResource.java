package com.example.demo.resources;

import com.example.demo.domain.User;
import com.example.demo.exceptions.MyBadRequestException;
import com.example.demo.exceptions.MyResourceNotFoundException;
import com.example.demo.inputs.UpdateNameInput;
import com.example.demo.outputs.MessageOutput;
import com.example.demo.outputs.NameOutput;
import com.example.demo.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class IdentityResource {

    @Autowired
    AuthService authService;

    @GetMapping("/name")
    @Operation(
            security = {@SecurityRequirement(name = "BearerJWT")}
    )
    public ResponseEntity<NameOutput> getName(HttpServletRequest request) throws MyResourceNotFoundException {
        String phone = (String) request.getAttribute("phoneNumber");
        User user = authService.getUserByPhone(phone);
        return new ResponseEntity<>(new NameOutput(user.name()), HttpStatus.OK);
    }

    @PutMapping("/name")
    @Operation(
            security = {@SecurityRequirement(name = "BearerJWT")}
    )
    public ResponseEntity<MessageOutput> updateName(HttpServletRequest request, @RequestBody UpdateNameInput updateNameInput) throws MyBadRequestException {
        String phone = (String) request.getAttribute("phoneNumber");
        authService.updateUserName(phone, updateNameInput.name());
        return new ResponseEntity<>(new MessageOutput("success"), HttpStatus.OK);
    }
}

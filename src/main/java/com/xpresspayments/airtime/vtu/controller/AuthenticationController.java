package com.xpresspayments.airtime.vtu.controller;

import com.xpresspayments.airtime.vtu.model.request.LoginRequest;
import com.xpresspayments.airtime.vtu.model.request.SignUpRequest;
import com.xpresspayments.airtime.vtu.service.auth.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    // Dependency Injection of Authentication Service Class
    private final AuthenticationService authenticationService;

    @Operation(summary = "User attempts to signup")
    @RequestMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest signUpRequest){
        return new ResponseEntity<>(authenticationService.signUp(signUpRequest),
                HttpStatus.CREATED);
    }

    @Operation(summary = "User attempts to login")
    @RequestMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(authenticationService.login(loginRequest),
                HttpStatus.OK);
    }
}

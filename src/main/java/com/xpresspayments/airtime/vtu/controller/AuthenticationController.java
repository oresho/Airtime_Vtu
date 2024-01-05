package com.xpresspayments.airtime.vtu.controller;

import com.xpresspayments.airtime.vtu.model.request.ChangePasswordRequest;
import com.xpresspayments.airtime.vtu.model.request.LoginRequest;
import com.xpresspayments.airtime.vtu.model.request.ResetPasswordRequest;
import com.xpresspayments.airtime.vtu.model.request.SignUpRequest;
import com.xpresspayments.airtime.vtu.service.auth.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "User attempts to signup")
    @RequestMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest signUpRequest){
        return new ResponseEntity<>(authenticationService.signUp(signUpRequest),
                HttpStatus.CREATED);
    }

    @Operation(summary = "User attempts to login")
    @RequestMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(authenticationService.login(loginRequest),
                HttpStatus.OK);
    }
//
//    @Operation(summary = "User attempts to send otp to email")
//    @RequestMapping(value = "/forgot-password", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
//    private ResponseEntity<?> forgotPassword(@RequestParam String email){
//        return new ResponseEntity<>(authenticationService.forgotPassword(email),
//                HttpStatus.OK);
//    }
//
//    @Operation(summary = "User attempts to verify otp")
//    @RequestMapping(value = "/verify-OTP", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
//    private ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest){
//        return new ResponseEntity<>(authenticationService.resetPassword(resetPasswordRequest.getToken(), resetPasswordRequest.getEmail()),
//                HttpStatus.OK);
//    }
//
//    @Operation(summary = "User attempts to change password")
//    @RequestMapping(value = "/change-password", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
//    private ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest){
//        return new ResponseEntity<>(authenticationService.changePassword(changePasswordRequest.getNewPassword(), changePasswordRequest.getEmail()),
//                HttpStatus.OK);
//    }
//
//    @Operation(summary = "Get User profile")
//    @RequestMapping(value = "/user-profile", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
//    private ResponseEntity<?> getUserProfile(){
//        return new ResponseEntity<>(authenticationService.getUserProfile(),
//                HttpStatus.OK);
//    }
}

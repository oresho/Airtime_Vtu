package com.xpresspayments.airtime.vtu.service.auth;


import com.xpresspayments.airtime.vtu.model.request.LoginRequest;
import com.xpresspayments.airtime.vtu.model.request.SignUpRequest;
import com.xpresspayments.airtime.vtu.model.response.ApiResponseDto;

public interface AuthenticationService {
    ApiResponseDto<?> signUp(SignUpRequest signUpRequest);
    ApiResponseDto<?> login(LoginRequest loginRequest);
}

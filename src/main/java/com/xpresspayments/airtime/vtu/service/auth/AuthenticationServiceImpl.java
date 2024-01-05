package com.xpresspayments.airtime.vtu.service.auth;

import com.xpresspayments.airtime.vtu.exceptionHandler.ApplicationException;
import com.xpresspayments.airtime.vtu.model.entity.AppUser;
import com.xpresspayments.airtime.vtu.model.repository.AppUserRepository;
import com.xpresspayments.airtime.vtu.model.request.LoginRequest;
import com.xpresspayments.airtime.vtu.model.request.SignUpRequest;
import com.xpresspayments.airtime.vtu.model.response.ApiResponseDto;
import com.xpresspayments.airtime.vtu.model.response.AuthenticationResponse;
import com.xpresspayments.airtime.vtu.security.JwtService;
import com.xpresspayments.airtime.vtu.service.appuser.AppUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AppUserService appUserService;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public ApiResponseDto<?> signUp(SignUpRequest signUpRequest) {
        return appUserService.create(signUpRequest);
    }

    @Override
    public ApiResponseDto<?> login(LoginRequest loginRequest) {
        AppUser appUser = appUserRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()-> new ApplicationException("User is not registered"));
        if(!passwordEncoder.matches(loginRequest.getPassword(),appUser.getPasswordHash())){
            throw  new ApplicationException("Invalid Credentials");
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(appUser.getEmail(),
                appUser.getPasswordHash());
        User user = new User(appUser.getEmail(), appUser.getPasswordHash(), List.of(new SimpleGrantedAuthority("USER")));
        String accessToken = jwtService.generateToken(user);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(appUser.getFullName(), accessToken);
        return new ApiResponseDto<>(
                "Successfully logged in User",
                HttpStatus.OK.value(),
                authenticationResponse
        );
    }
}

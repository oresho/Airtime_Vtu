package com.xpresspayments.airtime.vtu;

import com.xpresspayments.airtime.vtu.exceptionHandler.ApplicationException;
import com.xpresspayments.airtime.vtu.model.entity.AppUser;
import com.xpresspayments.airtime.vtu.model.repository.AppUserRepository;
import com.xpresspayments.airtime.vtu.model.request.LoginRequest;
import com.xpresspayments.airtime.vtu.model.request.SignUpRequest;
import com.xpresspayments.airtime.vtu.model.response.ApiResponseDto;
import com.xpresspayments.airtime.vtu.model.response.AppUserResponse;
import com.xpresspayments.airtime.vtu.model.response.AuthenticationResponse;
import com.xpresspayments.airtime.vtu.security.JwtService;
import com.xpresspayments.airtime.vtu.service.appuser.AppUserService;
import com.xpresspayments.airtime.vtu.service.auth.AuthenticationServiceImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Key;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static java.util.Date.from;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceImplTest {
    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Mock
    private AppUserService appUserService;

    private final String jwtSigningKey = "fakesirh9d7gq3euobdgd2u3b2u3dg9qdg9Q3gninkey";
    private final Long jwtExpirationInMillis = 600000L;

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Mock
    private JwtService jwtService;

    @Test
    public void shouldSignUpUser() {
        // get sign up details
        SignUpRequest signUpRequest = getSignUpRequest();

        ApiResponseDto<?> expectedResponse = new ApiResponseDto<>("Successfully Signed up User", HttpStatus.CREATED.value(), mapToAppUserResponse(getAppUser()));
        when(appUserService.create(signUpRequest)).thenReturn(new ApiResponseDto<>());

        // Act
        ApiResponseDto<?> response = authenticationService.signUp(signUpRequest);

        // Assert
        assertEquals(expectedResponse.getClass(), response.getClass());

        // Verify that appUserService.create method was called
        verify(appUserService, times(1)).create(signUpRequest);
    }

    @Test
    public void shouldLoginUser() {
        // get login details
        LoginRequest loginRequest = getLoginRequest();

        AppUser appUser = new AppUser();
        appUser.setEmail("Test");
        String passwordHash = new BCryptPasswordEncoder().encode("Test");
        appUser.setPasswordHash(passwordHash);

        when(appUserRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(appUser));
        when(passwordEncoder.matches(loginRequest.getPassword(), appUser.getPasswordHash())).thenReturn(true);

        User user = new User(appUser.getEmail(), appUser.getPasswordHash(), List.of(new SimpleGrantedAuthority("USER")));
        String accessToken = Jwts.builder().setClaims(new HashMap<>()).setSubject(appUser.getEmail())
                .setIssuedAt(from(Instant.now()))
                .setExpiration(java.sql.Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();

        when(jwtService.generateToken(user)).thenReturn(accessToken);

        AuthenticationResponse expectedResponse = new AuthenticationResponse(appUser.getFullName(), accessToken);
        ApiResponseDto<?> expectedApiResponse = new ApiResponseDto<>("Successfully logged in User", HttpStatus.OK.value(), expectedResponse);

        // Act
        ApiResponseDto<?> response = authenticationService.login(loginRequest);

        // Assert
        assertEquals(expectedApiResponse, response);

        // Verify that appUserRepository.findByEmail and jwtService.generateToken methods were called
        verify(appUserRepository, times(1)).findByEmail(loginRequest.getEmail());
        verify(jwtService, times(1)).generateToken(user);
    }

    private LoginRequest getLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("Test");
        loginRequest.setPassword("Test");
        return loginRequest;
    }
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Test
    public void shouldFailLogin_Unregistered_User() {
        // get login details
        LoginRequest loginRequest = getLoginRequest();

        // mock user does not exist scenario
        when(appUserRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ApplicationException.class, () -> authenticationService.login(loginRequest));
    }

    @Test
    public void shouldFailLogin_Invalid_Credentials() {
        // get login details
        LoginRequest loginRequest = getLoginRequest();

        AppUser appUser = new AppUser();
        appUser.setEmail("test@example.com");
        appUser.setPasswordHash("fake_mocked_Password_Hash_that_doesn't_match");

        //mock invalid credentials scenario
        when(appUserRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(appUser));
        when(passwordEncoder.matches(loginRequest.getPassword(), appUser.getPasswordHash())).thenReturn(false);

        // Act and Assert
        assertThrows(ApplicationException.class, () -> authenticationService.login(loginRequest));
    }

    @Test
    public void shouldFailSignUp_Duplicate_PhoneNo() {
        // get signup details
        SignUpRequest signUpRequest = getSignUpRequest();

        // mock behaviour
        when(appUserService.create(signUpRequest)).thenThrow(new ApplicationException("This phone number is already used"));

        // Act and Assert
        assertThrows(ApplicationException.class, () -> authenticationService.signUp(signUpRequest));
    }

    @Test
    public void shouldFailSignUp_Duplicate_Email() {
        // get signup details
        SignUpRequest signUpRequest = getSignUpRequest();

//        mock behaviour
        when(appUserService.create(signUpRequest)).thenThrow(new ApplicationException("This email is already used"));

        // Act and Assert
        assertThrows(ApplicationException.class, () -> authenticationService.signUp(signUpRequest));
    }

    private AppUser getAppUser() {
        AppUser appUser = new AppUser();
        appUser.setFullName("Test Test");
        appUser.setEmail("Test");
        appUser.setPhoneNo("Test");
        appUser.setPasswordHash("Test");
        return appUser;
    }

    private AppUserResponse mapToAppUserResponse(AppUser appUser) {
        AppUserResponse appUserResponse = new AppUserResponse();
        appUserResponse.setFullName(appUser.getFullName());
        appUserResponse.setEmail(appUser.getEmail());
        appUserResponse.setPhoneNo(appUser.getPhoneNo());
        return appUserResponse;
    }

    private SignUpRequest getSignUpRequest() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setFirstname("Test");
        signUpRequest.setLastname("Test");
        signUpRequest.setEmail("Test");
        signUpRequest.setPhoneNo("Test");
        signUpRequest.setPassword("Test");
        return signUpRequest;
    }
}
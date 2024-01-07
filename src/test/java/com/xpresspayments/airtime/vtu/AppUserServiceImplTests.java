package com.xpresspayments.airtime.vtu;

import com.xpresspayments.airtime.vtu.model.entity.AppUser;
import com.xpresspayments.airtime.vtu.model.repository.AppUserRepository;
import com.xpresspayments.airtime.vtu.model.request.SignUpRequest;
import com.xpresspayments.airtime.vtu.model.response.ApiResponseDto;
import com.xpresspayments.airtime.vtu.model.response.AppUserResponse;
import com.xpresspayments.airtime.vtu.service.appuser.AppUserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppUserServiceImplTests {
    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AppUserServiceImpl appUserService;

    @Captor
    private ArgumentCaptor<AppUser> appUserArgumentCaptor;

@Test
    public void shouldCreateAppUser() {
        // get sample sign up details
        SignUpRequest signUpRequest = getSignUpRequest();

        // Mock the behavior of appUserRepository
        when(appUserRepository.findByPhoneNo(signUpRequest.getPhoneNo())).thenReturn(Optional.empty());
        when(appUserRepository.findByEmail(signUpRequest.getEmail())).thenReturn(Optional.empty());

        AppUser savedAppUser = getAppUser();
        when(appUserRepository.save(savedAppUser)).thenReturn(savedAppUser);

        // Mock the behavior of passwordEncoder
        when(passwordEncoder.encode(signUpRequest.getPassword())).thenReturn(savedAppUser.getPasswordHash());

//        call service
        ApiResponseDto<?> response = appUserService.create(signUpRequest);

        // Assert
        assertEquals("Successfully Signed up User", response.getMessage());
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
        assertNotNull(response.getData());
        assertNotNull(response.getData());

        // Assert on the AppUserResponse data
        AppUserResponse appUserResponse = mapToAppUserResponse(savedAppUser);
        assertEquals(savedAppUser.getFullName(), appUserResponse.getFullName());
        assertEquals(savedAppUser.getEmail(), appUserResponse.getEmail());
        assertEquals(savedAppUser.getPhoneNo(), appUserResponse.getPhoneNo());


//        // Verify that the repository save method was called
        verify(appUserRepository, times(1)).save(appUserArgumentCaptor.capture());
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

}

package com.xpresspayments.airtime.vtu.service.appuser;

import com.xpresspayments.airtime.vtu.exceptionHandler.ApplicationException;
import com.xpresspayments.airtime.vtu.model.entity.AppUser;
import com.xpresspayments.airtime.vtu.model.repository.AppUserRepository;
import com.xpresspayments.airtime.vtu.model.request.SignUpRequest;
import com.xpresspayments.airtime.vtu.model.response.ApiResponseDto;
import com.xpresspayments.airtime.vtu.model.response.AppUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ApiResponseDto<?> create(SignUpRequest signUpRequest) {
        if (appUserRepository.findByPhoneNo(signUpRequest.getPhoneNo()).isPresent()){
            throw new ApplicationException("This phone number is already used");
        }
        if (appUserRepository.findByEmail(signUpRequest.getEmail()).isPresent()){
            throw new ApplicationException("This email is already used");
        }
        AppUser savedAppUser = appUserRepository.save(mapToAppUser(signUpRequest));
        AppUserResponse appUserResponse = mapToAppUserResponse(savedAppUser);
        return new ApiResponseDto<>(
                "Successfully Signed up User",
                HttpStatus.CREATED.value(), appUserResponse
        );
    }

    private AppUserResponse mapToAppUserResponse(AppUser appUser) {
        AppUserResponse appUserResponse = new AppUserResponse();
        appUserResponse.setFullName(appUser.getFullName());
        appUserResponse.setEmail(appUser.getEmail());
        appUserResponse.setPhoneNo(appUser.getPhoneNo());
        appUserResponse.setLocation(appUser.getLocation());
        return appUserResponse;
    }

    private AppUser mapToAppUser(SignUpRequest signUpRequest){
        AppUser appUser = new AppUser();
        appUser.setFullName(signUpRequest.getFirstname() + " " + signUpRequest.getLastname());
        appUser.setEmail(signUpRequest.getEmail());
        appUser.setPhoneNo(signUpRequest.getPhoneNo());
        appUser.setLocation(signUpRequest.getLocation());
        appUser.setPasswordHash(passwordEncoder.encode(signUpRequest.getPassword()));
        return appUser;
    }
}

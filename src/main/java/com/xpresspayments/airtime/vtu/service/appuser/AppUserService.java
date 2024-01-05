package com.xpresspayments.airtime.vtu.service.appuser;


import com.xpresspayments.airtime.vtu.model.request.SignUpRequest;
import com.xpresspayments.airtime.vtu.model.response.ApiResponseDto;

public interface AppUserService {
    ApiResponseDto<?> create(SignUpRequest signUpRequest);
}

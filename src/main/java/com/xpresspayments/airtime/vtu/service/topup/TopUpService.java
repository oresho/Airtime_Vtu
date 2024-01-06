package com.xpresspayments.airtime.vtu.service.topup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xpresspayments.airtime.vtu.model.entity.Airtime;
import com.xpresspayments.airtime.vtu.model.request.TopUpRequest;
import com.xpresspayments.airtime.vtu.model.response.ApiResponseDto;

public interface TopUpService {
    ApiResponseDto<?> topUp(TopUpRequest topUpRequest, Airtime airtime) throws JsonProcessingException;
}

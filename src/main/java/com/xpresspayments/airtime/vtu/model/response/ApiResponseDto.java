package com.xpresspayments.airtime.vtu.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDto<T> {

    private String message;
    private Integer statusCode;
    private T data;
}

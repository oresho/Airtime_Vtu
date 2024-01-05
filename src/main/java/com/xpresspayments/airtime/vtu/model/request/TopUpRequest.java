package com.xpresspayments.airtime.vtu.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopUpRequest {
    @NotBlank(message = "phoneNumber is required")
    private String phoneNumber;
    @NotNull(message = "amount is required")
    private Integer amount;
}

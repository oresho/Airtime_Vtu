package com.xpresspayments.airtime.vtu.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VtuRequest {
    @NotBlank(message = "requestId is required")
    private String requestId;
    @NotBlank(message = "uniqueCode is required")
    private String uniqueCode;
    @NotNull(message = "details are required")
    private Details details;

    @Data
    @AllArgsConstructor
    public static class Details {
        @NotBlank(message = "phoneNumber is required")
        private String phoneNumber;
        @NotNull(message = "amount is required")
        private int amount;
    }
}


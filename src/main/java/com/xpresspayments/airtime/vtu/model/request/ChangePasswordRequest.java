package com.xpresspayments.airtime.vtu.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePasswordRequest {
    @NotBlank(message = "newPassword is required")
    private String newPassword;
    @NotBlank(message = "email is required")
    public String email;
}

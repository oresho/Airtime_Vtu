package com.xpresspayments.airtime.vtu.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "firstname is required")
    private String firstname;
    @NotBlank(message = "lastname is required")
    private String lastname;
    @NotBlank(message = "email is required")
    private String email;
    @NotBlank(message = "phoneNo is required")
    private String phoneNo;
    @NotBlank(message = "location is required")
    private String location;
    @NotBlank(message = "password is required")
    private String password;
}

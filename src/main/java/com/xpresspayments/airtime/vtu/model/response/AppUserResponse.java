package com.xpresspayments.airtime.vtu.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserResponse {
    private String fullName;
    private String email;
    private String phoneNo;
    private String location;
}

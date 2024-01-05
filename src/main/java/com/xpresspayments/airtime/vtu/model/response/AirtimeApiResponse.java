package com.xpresspayments.airtime.vtu.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AirtimeApiResponse {

    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("referenceId")
    private String referenceId;

    @JsonProperty("responseCode")
    private String responseCode;

    @JsonProperty("responseMessage")
    private String responseMessage;

    @JsonProperty("data")
    private AirtimeApiData data;

    public static class AirtimeApiData {

        @JsonProperty("amount")
        private double amount;

        @JsonProperty("phoneNumber")
        private String phoneNumber;

    }
}

package com.xpresspayments.airtime.vtu.service.topup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xpresspayments.airtime.vtu.model.entity.Airtime;
import com.xpresspayments.airtime.vtu.model.request.TopUpRequest;
import com.xpresspayments.airtime.vtu.model.request.VtuRequest;
import com.xpresspayments.airtime.vtu.model.response.AirtimeApiResponse;
import com.xpresspayments.airtime.vtu.model.response.ApiResponseDto;
import com.xpresspayments.airtime.vtu.service.hashgenerator.HashGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class TopUpServiceImpl implements TopUpService{
    private final WebClient webClient;
    private final ObjectMapper objectMapper; // convert object to json and json to object
    private final HashGeneratorService hashGeneratorService;

    @Value("${xpress_pubKey}")
    private String pubKey;

    @Value("${xpress_privKey}")
    private String privKey;

    // Inject WebClient into the constructor
    public TopUpServiceImpl(WebClient.Builder webClientBuilder, ObjectMapper objectMapper, HashGeneratorService hashGeneratorService) {
        this.webClient = webClientBuilder.baseUrl("https://billerstest.xpresspayments.com:9603/api/v1").build();
        this.objectMapper = objectMapper;
        this.hashGeneratorService = hashGeneratorService;
    }

    @Override
    public ApiResponseDto<?> topUp(TopUpRequest topUpRequest, Airtime airtime) throws JsonProcessingException {
        String authToken = "Bearer " + pubKey;
        VtuRequest vtuRequest = mapToVtuRequest(topUpRequest, airtime);
        String requestDto = objectMapper.writeValueAsString(vtuRequest);
        //convert request to JSON using gson or ObjectMapper
        String GENERATED_HMAC = hashGeneratorService.calculateHMAC512(requestDto, privKey);
        String jsonResponse = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/airtime/fulfil")
                        .build())
                .header(HttpHeaders.AUTHORIZATION, authToken)
                .header("PaymentHash", GENERATED_HMAC)
                .header("Channel", "API")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestDto))
                .retrieve()
                .bodyToMono(String.class)
                .block(); // block() is used here for simplicity; in a real application, you'd handle the response asynchronously.
        AirtimeApiResponse airtimeApiResponse = objectMapper.readValue(jsonResponse, AirtimeApiResponse.class);
        return new ApiResponseDto<>("Successfully sent Top Up Request for phoneNo: " + vtuRequest.getDetails().getPhoneNumber(),
                HttpStatus.OK.value(),
                airtimeApiResponse);
    }

    private VtuRequest mapToVtuRequest(TopUpRequest topUpRequest, Airtime airtime) {
        String requestId = RandomStringUtils.randomNumeric(6);
        VtuRequest vtuRequest = new VtuRequest();
        vtuRequest.setRequestId(requestId);
        vtuRequest.setUniqueCode(airtime.getValue());
        vtuRequest.setDetails(
                new VtuRequest.Details(topUpRequest.getPhoneNumber(),
                        topUpRequest.getAmount()));
        return vtuRequest;
    }
}

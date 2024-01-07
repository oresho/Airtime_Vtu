package com.xpresspayments.airtime.vtu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xpresspayments.airtime.vtu.model.entity.Airtime;
import com.xpresspayments.airtime.vtu.model.request.LoginRequest;
import com.xpresspayments.airtime.vtu.model.request.SignUpRequest;
import com.xpresspayments.airtime.vtu.model.request.TopUpRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void testUserFlow() throws Exception {
        // Sign Up
        SignUpRequest signUpRequest = getSignUpRequest();
        // Perform Sign Up
        ResultActions signUpResult = mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Successfully Signed up User"));



        // Login
        LoginRequest loginRequest = getLoginRequest();
        // Perform Login
        ResultActions resultActions = mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Successfully logged in User"))
                .andExpect(jsonPath("$.data.accessToken").exists());

        // get access token from login
        String accessToken = getAccessToken(resultActions);

        // Create a TopUpRequest
        TopUpRequest topUpRequest = getTopUpRequest();

        // Perform TopUp
        mockMvc.perform(post("/api/v1/topup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(topUpRequest))
                        .queryParam("airtime", String.valueOf(Airtime.MTN))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer "+ accessToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Successfully sent Top Up Request for phoneNo: " + topUpRequest.getPhoneNumber()))
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data").exists());

    }

    private static TopUpRequest getTopUpRequest() {
        TopUpRequest topUpRequest = new TopUpRequest();
        topUpRequest.setPhoneNumber("08033333333");
        topUpRequest.setAmount(100);
        return topUpRequest;
    }

    private static String getAccessToken(ResultActions resultActions) throws UnsupportedEncodingException {
        MvcResult mvcResult = resultActions.andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        // Find the starting index of "accessToken"
        int startIndex = content.indexOf("\"accessToken\":\"") + "\"accessToken\":\"".length();

        // Find the ending index of "accessToken"
        int endIndex = content.indexOf("\"", startIndex);

        // Use substring to extract the accessToken
        return content.substring(startIndex, endIndex);
    }

    private LoginRequest getLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("Test");
        loginRequest.setPassword("Test");
        return loginRequest;
    }

    private SignUpRequest getSignUpRequest() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setFirstname("Test");
        signUpRequest.setLastname("Test");
        signUpRequest.setEmail("Test");
        signUpRequest.setPhoneNo("Test");
        signUpRequest.setPassword("Test");
        return signUpRequest;
    }
}

package com.xpresspayments.airtime.vtu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xpresspayments.airtime.vtu.model.entity.Airtime;
import com.xpresspayments.airtime.vtu.model.request.TopUpRequest;
import com.xpresspayments.airtime.vtu.service.topup.TopUpService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/topup")
public class TopUpController {
    private final TopUpService topUpService;

    @Operation(summary = "User attempts to top up number")
    @PostMapping
    public ResponseEntity<?> topUp(@Valid @RequestBody TopUpRequest topUpRequest, @NotNull @RequestParam Airtime airtime) throws JsonProcessingException {
        return new ResponseEntity<>(topUpService.topUp(topUpRequest, airtime),
                HttpStatus.OK);
    }
}

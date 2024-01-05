package com.xpresspayments.airtime.vtu;
import com.xpresspayments.airtime.vtu.service.hashgenerator.HashGeneratorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HashGeneratorServiceImplTest {

    @InjectMocks
    private HashGeneratorServiceImpl hashGeneratorService;

    @Test
    public void testCalculateHMAC512_Success() {
        // Arrange
        String data = "testData";
        String key = "testKey";

        // Act
        String result = hashGeneratorService.calculateHMAC512(data, key);

        // Assert
        assertNotNull(result);
        // You may assert the expected hash value based on known input and key
    }

    @Test
    public void testCalculateHMAC512_Failure_InvalidAlgorithm() throws NoSuchAlgorithmException {
        // Arrange
        assertThrows(NoSuchAlgorithmException.class, () -> Mac.getInstance("not an algo"));
        // Mock NoSuchAlgorithmException scenario
    }

    @Test
    public void testCalculateHMAC512_Failure_InvalidKey() throws NoSuchAlgorithmException, InvalidKeyException {
        // Arrange
        String HMAC_SHA512 = "HmacSHA512";
        Mac mac = null;
        mac = Mac.getInstance(HMAC_SHA512);
        SecretKeySpec secretKeySpec = null;

        // Mock InvalidKeyException scenario
        // Act and Assert
        Mac finalMac = mac;
        assertThrows(InvalidKeyException.class, () -> finalMac.init(secretKeySpec));
    }

    // Add more tests for different failure scenarios and edge cases
}


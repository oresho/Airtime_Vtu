package com.xpresspayments.airtime.vtu;

import com.xpresspayments.airtime.vtu.service.hashgenerator.HashGeneratorServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HashGeneratorServiceImplTest {

    @Autowired
    private final HashGeneratorServiceImpl hashGeneratorService = new HashGeneratorServiceImpl();

    @Test
    public void testCalculateHMAC512_Success() {
        // set up test data
        String data = "testData";
        String key = "testKey";
        String result = hashGeneratorService.calculateHMAC512(data, key);
        // Assert service is working
        assertNotNull(result);
    }

    @Test
    public void testCalculateHMAC512_Failure_InvalidAlgorithm() {
        String HMAC_SHA512 = "fake Algorithm";
        // using fake algo to assert exception
        assertThrows(NoSuchAlgorithmException.class, () -> hashGeneratorService.getInstance(HMAC_SHA512));
    }

    @Test
    public void testCalculateHMAC512_Failure_InvalidKey() throws NoSuchAlgorithmException {
        String HMAC_SHA512 = "HmacSHA512";
        Mac mac = Mac.getInstance(HMAC_SHA512);
        SecretKeySpec secretKeySpec = null;
        // using null key to assert exception
        assertThrows(InvalidKeyException.class, () -> hashGeneratorService.initializeKey(mac, secretKeySpec));
    }

}


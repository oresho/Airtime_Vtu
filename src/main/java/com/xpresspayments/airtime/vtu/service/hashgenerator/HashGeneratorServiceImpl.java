package com.xpresspayments.airtime.vtu.service.hashgenerator;

import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class HashGeneratorServiceImpl implements HashGeneratorService{
    @Override
    public String calculateHMAC512(String data, String key) {
        String HMAC_SHA512 = "HmacSHA512";
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), HMAC_SHA512);
        Mac mac = null;
        try {
            mac = getInstance(HMAC_SHA512);
            initializeKey(mac, secretKeySpec);
            return Hex.encodeHexString(mac.doFinal(data.getBytes()));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public void initializeKey(Mac mac, SecretKeySpec secretKeySpec) throws InvalidKeyException {
        mac.init(secretKeySpec);
    }

    public Mac getInstance(String algorithm) throws NoSuchAlgorithmException {
        return Mac.getInstance(algorithm);
    }
}

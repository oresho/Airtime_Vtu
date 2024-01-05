package com.xpresspayments.airtime.vtu.service.hashgenerator;

public interface HashGeneratorService {
    String calculateHMAC512(String data, String key);
}

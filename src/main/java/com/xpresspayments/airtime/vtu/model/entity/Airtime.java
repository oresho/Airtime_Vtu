package com.xpresspayments.airtime.vtu.model.entity;

public enum Airtime {
    MTN("MTN_24207"),
    GLO("GLO_30387"),
    AIRTEL("AIRTEL_22689"),
    NINE_MOBILE("9MOBILE_69358");

    private final String value;

    Airtime(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

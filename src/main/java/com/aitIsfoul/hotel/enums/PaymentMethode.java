package com.aitIsfoul.hotel.enums;

public enum PaymentMethode {
    BANK_TRANSFER("Bank Transfer"),
    DEBIT_CARD("Debit Card"),
    CASH("Cash");

    private final String methodName;

    PaymentMethode(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

}
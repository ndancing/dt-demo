package com.example.dt.demo.core.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatusEnum {

    PENDING_PAYMENT(1, "Pending Payment"),

    PAYING(2, "Paying"),

    PAYMENT_FAIL(3, "Payment Fail"),

    PAYMENT_SUCCESS(4, "Payment Success");

    private final int code;

    private final String desc;

    public static OrderStatusEnum findByValue(int code) {
        switch (code) {
            case 1:
                return PENDING_PAYMENT;
            case 2:
                return PAYING;
            case 3:
                return PAYMENT_FAIL;
            case 4:
                return PAYMENT_SUCCESS;
            default:
                return null;
        }
    }
}
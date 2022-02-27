package com.example.dt.demo.app.order.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProcessOrderResponse {

    private int code;
    private String msg;
    private long latency;
    private String orderId;
}

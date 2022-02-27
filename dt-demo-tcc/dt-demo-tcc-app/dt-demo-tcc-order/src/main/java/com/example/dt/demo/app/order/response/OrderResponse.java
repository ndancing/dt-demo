package com.example.dt.demo.app.order.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class OrderResponse {

    private String id;

    private String createTime;

    private String status;

    private String productId;

    private long totalAmount;

    private int count;

    private String userId;

}

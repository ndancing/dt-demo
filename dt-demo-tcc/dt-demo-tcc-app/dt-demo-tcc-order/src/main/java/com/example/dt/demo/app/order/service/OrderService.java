package com.example.dt.demo.app.order.service;

import com.example.dt.demo.app.order.response.OrderResponse;
import com.example.dt.demo.app.order.response.ProcessOrderResponse;

public interface OrderService {

    ProcessOrderResponse processOrder(String userId, String productId, int count, double amount);

    OrderResponse getOrder(String orderId);

}

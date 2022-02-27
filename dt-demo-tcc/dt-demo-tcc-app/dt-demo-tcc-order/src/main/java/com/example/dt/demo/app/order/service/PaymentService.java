package com.example.dt.demo.app.order.service;

import com.example.dt.demo.core.order.entity.Order;

public interface PaymentService {

    void makePayment(Order order);

}

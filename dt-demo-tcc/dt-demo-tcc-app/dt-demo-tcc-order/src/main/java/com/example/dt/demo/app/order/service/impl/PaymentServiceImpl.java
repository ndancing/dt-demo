package com.example.dt.demo.app.order.service.impl;

import org.dromara.hmily.annotation.HmilyTCC;
import com.example.dt.demo.core.order.entity.Order;
import com.example.dt.demo.core.order.enums.OrderStatusEnum;
import com.example.dt.demo.core.order.repository.OrderRepository;
import com.example.dt.demo.app.order.client.AccountGrpcClient;
import com.example.dt.demo.app.order.client.InventoryGrpcClient;
import com.example.dt.demo.app.order.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final OrderRepository orderRepository;

    @Autowired
    private AccountGrpcClient accountGrpcClient;

    @Autowired
    private InventoryGrpcClient inventoryGrpcClient;

    @Autowired(required = false)
    public PaymentServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @HmilyTCC(confirmMethod = "confirmOrderStatus", cancelMethod = "cancelOrderStatus")
    public void makePayment(Order order) {
        updateOrderStatus(order, OrderStatusEnum.PAYING);
        accountGrpcClient.payment(String.valueOf(order.getUserId()), String.valueOf(order.getTotalAmount().doubleValue()));
        inventoryGrpcClient.decrease(order.getProductId(), order.getCount());
    }

    private void updateOrderStatus(Order order, OrderStatusEnum orderStatus) {
        order.setStatus(orderStatus.getCode());
        orderRepository.updateStatus(order);
    }

    public void confirmOrderStatus(Order order) {
        updateOrderStatus(order, OrderStatusEnum.PAYMENT_SUCCESS);
    }

    public void cancelOrderStatus(Order order) {
        updateOrderStatus(order, OrderStatusEnum.PAYMENT_FAIL);
    }
}

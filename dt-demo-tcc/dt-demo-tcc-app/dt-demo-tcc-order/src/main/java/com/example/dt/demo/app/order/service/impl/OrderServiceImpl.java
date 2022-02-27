package com.example.dt.demo.app.order.service.impl;

import com.example.dt.demo.app.order.response.OrderResponse;
import com.example.dt.demo.app.order.response.ProcessOrderResponse;
import com.example.dt.demo.app.order.service.PaymentService;
import com.example.dt.demo.core.order.entity.Order;
import com.example.dt.demo.core.order.enums.OrderStatusEnum;
import com.example.dt.demo.core.order.repository.OrderRepository;
import com.example.dt.demo.app.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final PaymentService paymentService;

    @Autowired(required = false)
    public OrderServiceImpl(OrderRepository orderRepository, PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.paymentService = paymentService;
    }

    @Override
    public ProcessOrderResponse processOrder(String userId, String productId, int count, double amount) {
        Order order = saveOrder(userId, productId, count, amount);
        long start = System.currentTimeMillis();
        paymentService.makePayment(order);
        long processingTime = System.currentTimeMillis() - start;
        return new ProcessOrderResponse(200, "success", processingTime, order.getId());
    }

    @Override
    public OrderResponse getOrder(String orderId) {
        Order order = orderRepository.findByOrderId(orderId);
        if (order == null) {
            throw new IllegalArgumentException();
        }
        return new OrderResponse(
                order.getId(),
                order.getCreateTime().toString(),
                Objects.requireNonNull(OrderStatusEnum.findByValue(order.getStatus()), "Invalid order status").getDesc(),
                order.getProductId(),
                order.getTotalAmount().longValue(),
                order.getCount(),
                order.getUserId()
        );
    }

    public Order saveOrder(String userId, String productId, int count, double amount) {
        final Order order = buildOrder(userId, productId, count, BigDecimal.valueOf(amount));
        orderRepository.save(order);
        return order;
    }

    private Order buildOrder(String userId, String productId, Integer count, BigDecimal amount) {
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setCreateTime(new Date());
        order.setProductId(productId);
        order.setStatus(OrderStatusEnum.PENDING_PAYMENT.getCode());
        order.setTotalAmount(amount);
        order.setCount(count);
        order.setUserId(userId);
        return order;
    }
}

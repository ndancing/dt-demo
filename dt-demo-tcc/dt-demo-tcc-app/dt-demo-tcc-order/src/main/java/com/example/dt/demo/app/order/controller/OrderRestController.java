package com.example.dt.demo.app.order.controller;

import com.example.dt.demo.app.order.response.OrderResponse;
import com.example.dt.demo.app.order.response.ProcessOrderResponse;
import com.example.dt.demo.app.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderRestController {

    @Autowired
    OrderService orderService;

    @PostMapping(value = "/process")
    public ProcessOrderResponse processOrder(@RequestParam(value = "userId") String userId,
                                             @RequestParam(value = "productId") String productId,
                                             @RequestParam(value = "count") Integer count,
                                             @RequestParam(value = "amount") double amount) {
        return orderService.processOrder(userId, productId, count, amount);
    }

    @GetMapping(value = "/{id}")
    public OrderResponse getOrder(@PathVariable String id) {
        return orderService.getOrder(id);
    }

}

package com.example.dt.demo.app.inventory.controller;

import com.example.dt.demo.app.inventory.response.InventoryResponse;
import com.example.dt.demo.app.inventory.service.InventoryBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryRestController {

    @Autowired
    InventoryBizService inventoryBizService;

    @GetMapping(value = "/{productId}")
    public InventoryResponse getInventory(@PathVariable String productId) {
        Assert.hasText(productId, "productId cannot be empty");
        return inventoryBizService.getInventory(productId);
    }

    @PostMapping(value="/setMockException")
    public String setMockException(@RequestParam(value = "flag") boolean flag) {
        inventoryBizService.mockException(flag);
        return "success";
    }

    @PostMapping(value="/{productId}/setTotalInventory")
    public String setTotalInventory(@PathVariable String productId, @RequestParam(value = "amount") int amount) {
        Assert.hasText(productId, "productId cannot empty");
        Assert.isTrue(amount > 0, "amount must greater than 0");
        inventoryBizService.setInventory(productId, amount);
        return "success";
    }

}

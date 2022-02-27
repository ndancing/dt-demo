package com.example.dt.demo.app.inventory.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InventoryResponse {

    private String productId;

    private Integer totalInventory;

    private Integer pendingPaymentInventory;

}

package com.example.dt.demo.app.inventory.service;

import com.example.dt.demo.app.inventory.response.InventoryResponse;
import com.example.dt.demo.core.inventory.dto.InventoryDTO;

public interface InventoryBizService {
    
    boolean decrease(InventoryDTO inventoryDTO);

    InventoryResponse getInventory(String productId);

    void mockException(boolean flag);

    void setInventory(String productId, int amount);
    
}

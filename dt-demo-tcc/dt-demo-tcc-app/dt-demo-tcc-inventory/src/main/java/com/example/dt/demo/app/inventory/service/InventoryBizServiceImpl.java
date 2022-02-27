package com.example.dt.demo.app.inventory.service;

import com.example.dt.demo.app.inventory.response.InventoryResponse;
import com.example.dt.demo.core.inventory.dto.InventoryDTO;
import com.example.dt.demo.core.inventory.entity.Inventory;
import com.example.dt.demo.core.inventory.repository.InventoryRepository;
import org.dromara.hmily.annotation.HmilyTCC;
import org.dromara.hmily.common.exception.HmilyRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InventoryBizServiceImpl implements InventoryBizService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryBizServiceImpl.class);

    private boolean mockException = false;

    private final InventoryRepository inventoryRepository;

    @Autowired(required = false)
    public InventoryBizServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @HmilyTCC(confirmMethod = "confirmInventory", cancelMethod = "cancelInventory")
    public boolean decrease(InventoryDTO inventoryDTO) {
        if (inventoryRepository.decrease(inventoryDTO) > 0) {
            return true;
        }
        throw new HmilyRuntimeException("No stock available!");
    }

    @Override
    public InventoryResponse getInventory(String productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId);
        if (inventory == null) {
            throw new IllegalArgumentException();
        }
        return new InventoryResponse(
                inventory.getProductId(),
                inventory.getTotalInventory(),
                inventory.getLockInventory()
        );
    }

    @Override
    public void mockException(boolean flag) {
        this.mockException = flag;
    }

    @Override
    public void setInventory(String productId, int amount) {
        Inventory inventory = inventoryRepository.findByProductId(productId);
        if (inventory == null) {
            throw new IllegalArgumentException();
        }
        inventory.setTotalInventory(amount);
        inventoryRepository.updateInventory(inventory);
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean confirmInventory(InventoryDTO inventoryDTO) {
        if (mockException) {
            throw new HmilyRuntimeException("An error occurred!");
        }
        inventoryRepository.confirm(inventoryDTO);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean cancelInventory(InventoryDTO inventoryDTO) {
        if (mockException) {
            throw new HmilyRuntimeException("An error occurred!");
        }
        inventoryRepository.cancel(inventoryDTO);
        return true;
    }
    
}

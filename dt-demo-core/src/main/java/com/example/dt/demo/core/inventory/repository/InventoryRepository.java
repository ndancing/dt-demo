package com.example.dt.demo.core.inventory.repository;

import com.example.dt.demo.core.inventory.dto.InventoryDTO;
import com.example.dt.demo.core.inventory.entity.Inventory;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface InventoryRepository {

    @Update("update inventory set total_inventory = total_inventory - #{count}," +
            " lock_inventory= lock_inventory + #{count} " +
            " where product_id =#{productId} and total_inventory >= #{count}  ")
    int decrease(InventoryDTO inventoryDTO);

    @Update("update inventory set total_inventory = total_inventory - #{count} " +
            " where product_id =#{productId} and total_inventory >= #{count}  ")
    int decreaseTAC(InventoryDTO inventoryDTO);

    @Update("update inventory set total_inventory = total_inventory - #{count}" +
            " where product_id =#{productId} and total_inventory >= #{count}  ")
    int testDecrease(InventoryDTO inventoryDTO);

    @Update("update inventory set " +
            " lock_inventory = lock_inventory - #{count} " +
            " where product_id =#{productId} and lock_inventory >= #{count} ")
    int confirm(InventoryDTO inventoryDTO);

    @Update("update inventory set total_inventory = total_inventory + #{count}," +
            " lock_inventory= lock_inventory - #{count} " +
            " where product_id =#{productId} and lock_inventory >= #{count} ")
    int cancel(InventoryDTO inventoryDTO);

    @Select("select id,product_id,total_inventory,lock_inventory from inventory where product_id =#{productId}")
    Inventory findByProductId(String productId);

    @Update("update inventory set total_inventory = #{totalInventory}" +
            " where product_id =#{productId}")
    int updateInventory(Inventory inventory);
}

package com.example.dt.demo.core.inventory.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Inventory implements Serializable {

    private static final long serialVersionUID = 6957734749389133832L;

    private Integer id;

    private String productId;

    private Integer totalInventory;

    private Integer lockInventory;
}

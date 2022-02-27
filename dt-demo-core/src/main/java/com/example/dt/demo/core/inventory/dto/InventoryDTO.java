package com.example.dt.demo.core.inventory.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class InventoryDTO implements Serializable {

    private static final long serialVersionUID = 8229355519336565493L;

    private String productId;

    private Integer count;
}

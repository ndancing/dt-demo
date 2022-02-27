package com.example.dt.demo.core.account.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class AccountNestedDTO implements Serializable {

    private static final long serialVersionUID = 7223470850578998427L;

    private String userId;

    private BigDecimal amount;

    private String productId;

    private Integer count;
}

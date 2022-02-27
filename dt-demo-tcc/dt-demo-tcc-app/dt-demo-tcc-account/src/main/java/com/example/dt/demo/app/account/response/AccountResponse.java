package com.example.dt.demo.app.account.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountResponse {

    private String userId;

    private long balance;

    private long pendingPaymentAmount;

}

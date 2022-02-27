package com.example.dt.demo.app.account.service;

import com.example.dt.demo.app.account.response.AccountResponse;
import com.example.dt.demo.core.account.dto.AccountDTO;

public interface AccountBizService {
    
    boolean payment(AccountDTO accountDTO);

    AccountResponse getAccount(String userId);

    void mockException(boolean flag);

    void setBalance(String userId, long amount);
    
}

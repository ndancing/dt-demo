package com.example.dt.demo.app.account.service;

import com.example.dt.demo.app.account.response.AccountResponse;
import com.example.dt.demo.core.account.dto.AccountDTO;
import com.example.dt.demo.core.account.entity.Account;
import com.example.dt.demo.core.account.repository.AccountRepository;
import org.dromara.hmily.annotation.HmilyTCC;
import org.dromara.hmily.common.exception.HmilyRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
public class AccountBizServiceImpl implements AccountBizService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountBizServiceImpl.class);

    private boolean mockException = false;

    private final AccountRepository accountRepository;

    @Autowired(required = false)
    public AccountBizServiceImpl(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @HmilyTCC(confirmMethod = "confirmAccountBalance", cancelMethod = "cancelAccountBalance")
    public boolean payment(AccountDTO accountDTO) {
        if (accountRepository.update(accountDTO) > 0) {
            return true;
        }
        throw new HmilyRuntimeException("Balance not enough for payment!");
    }

    @Override
    public AccountResponse getAccount(String userId) {
        Account account = accountRepository.findByUserId(userId);
        if (account == null) {
            throw new IllegalArgumentException();
        }
        return new AccountResponse(
                account.getUserId(),
                account.getBalance().longValue(),
                account.getFreezeAmount().longValue()
        );
    }

    @Override
    public void mockException(boolean flag) {
        this.mockException = flag;
    }

    @Override
    public void setBalance(String userId, long amount) {
        Account account = accountRepository.findByUserId(userId);
        if (account == null) {
            throw new IllegalArgumentException();
        }
        account.setBalance(new BigDecimal(amount));
        accountRepository.updateBalance(account);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean confirmAccountBalance(AccountDTO accountDTO) {
        if (mockException) {
            throw new HmilyRuntimeException("An error occurred!");
        }
        accountRepository.confirm(accountDTO);
        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean cancelAccountBalance(AccountDTO accountDTO) {
        if (mockException) {
            throw new HmilyRuntimeException("An error occurred!");
        }
        accountRepository.cancel(accountDTO);
        return Boolean.TRUE;
    }

}
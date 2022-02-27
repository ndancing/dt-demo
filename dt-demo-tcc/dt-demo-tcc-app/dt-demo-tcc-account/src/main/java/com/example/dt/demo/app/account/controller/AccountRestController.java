package com.example.dt.demo.app.account.controller;

import com.example.dt.demo.app.account.response.AccountResponse;
import com.example.dt.demo.app.account.service.AccountBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/account")
public class AccountRestController {

    @Autowired
    AccountBizService accountBizService;

    @GetMapping(value = "/{id}")
    public AccountResponse getAccount(@PathVariable String id) {
        Assert.hasText(id, "id cannot not empty");
        return accountBizService.getAccount(id);
    }

    @PostMapping(value="/setMockException")
    public String setMockException(@RequestParam(value = "flag") boolean flag) {
        accountBizService.mockException(flag);
        return "success";
    }

    @PostMapping(value="/{id}/setBalance")
    public String setBalance(@PathVariable String id, @RequestParam(value = "amount") long amount) {
        Assert.hasText(id, "id cannot empty");
        Assert.isTrue(amount > 0, "amount must greater than 0");
        accountBizService.setBalance(id, amount);
        return "success";
    }

}

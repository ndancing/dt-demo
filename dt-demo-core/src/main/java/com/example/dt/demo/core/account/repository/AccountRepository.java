package com.example.dt.demo.core.account.repository;

import com.example.dt.demo.core.account.dto.AccountDTO;
import com.example.dt.demo.core.account.entity.Account;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AccountRepository {

    @Update("update account set balance = balance - #{amount}," +
            " freeze_amount= freeze_amount + #{amount} ,update_time = now()" +
            " where user_id =#{userId} and balance >= #{amount}  ")
    int update(AccountDTO accountDTO);

    @Update("update account set balance = balance - #{amount}, update_time = now()" +
            " where user_id =#{userId} and balance >= #{amount}  ")
    int updateTAC(AccountDTO accountDTO);
    

    @Update("update account set balance = balance - #{amount}, update_time = now() " +
            " where user_id =#{userId} and balance >= #{amount}  ")
    int testUpdate(AccountDTO accountDTO);

    @Update("update account set " +
            " freeze_amount= freeze_amount - #{amount}" +
            " where user_id =#{userId} and freeze_amount >= #{amount} ")
    int confirm(AccountDTO accountDTO);

    @Update("update account set balance = balance + #{amount}," +
            " freeze_amount= freeze_amount -  #{amount} " +
            " where user_id =#{userId} and freeze_amount >= #{amount}")
    int cancel(AccountDTO accountDTO);

    @Select("select id,user_id,balance, freeze_amount from account where user_id =#{userId} limit 1")
    Account findByUserId(String userId);

    @Update("update account set balance=#{balance}" +
            " where user_id =#{userId}")
    int updateBalance(Account account);
}

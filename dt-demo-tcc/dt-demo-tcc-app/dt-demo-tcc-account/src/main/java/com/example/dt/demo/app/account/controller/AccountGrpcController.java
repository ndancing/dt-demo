package com.example.dt.demo.app.account.controller;

import com.example.dt.demo.app.account.service.AccountBizService;
import com.example.dt.demo.grpc.account.service.AccountRequest;
import com.example.dt.demo.grpc.account.service.AccountResponse;
import com.example.dt.demo.grpc.account.service.AccountServiceGrpc;
import io.grpc.stub.StreamObserver;
import com.example.dt.demo.core.account.dto.AccountDTO;
import org.dromara.hmily.common.exception.HmilyRuntimeException;
import org.dromara.hmily.grpc.filter.GrpcHmilyServerFilter;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@GRpcService(interceptors = {GrpcHmilyServerFilter.class})
public class AccountGrpcController extends AccountServiceGrpc.AccountServiceImplBase {
    
    @Autowired
    AccountBizService accountBizService;

    @Override
    public void payment(AccountRequest accountRequest, StreamObserver<AccountResponse> responseObserver) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAmount(new BigDecimal(accountRequest.getAmount()));
        accountDTO.setUserId(accountRequest.getUserId());
        try {
            AccountResponse response = AccountResponse.newBuilder().setResult(accountBizService.payment(accountDTO)).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new HmilyRuntimeException());
        }
    }

}

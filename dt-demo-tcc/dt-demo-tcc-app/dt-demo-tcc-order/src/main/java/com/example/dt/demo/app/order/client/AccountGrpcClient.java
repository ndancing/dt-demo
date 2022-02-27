package com.example.dt.demo.app.order.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.example.dt.demo.grpc.account.service.AccountRequest;
import com.example.dt.demo.grpc.account.service.AccountResponse;
import com.example.dt.demo.grpc.account.service.AccountServiceGrpc;
import org.dromara.hmily.grpc.client.GrpcHmilyClient;
import org.dromara.hmily.grpc.filter.GrpcHmilyTransactionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@PropertySource("classpath:application.yml")
public class AccountGrpcClient {

    @Value("${client.grpc.account.host}")
    private String host;

    @Value("${client.grpc.account.port}")
    private int port;

    private AccountServiceGrpc.AccountServiceBlockingStub accountServiceBlockingStub;
    
    @Autowired
    GrpcHmilyClient grpcClient;

    @PostConstruct
    private void init() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(host, port)
                .intercept(new GrpcHmilyTransactionFilter()).usePlaintext().build();
        accountServiceBlockingStub = AccountServiceGrpc.newBlockingStub(managedChannel);
    }

    public boolean payment(String userId, String amount) {
        AccountRequest request = AccountRequest.newBuilder().setUserId(userId).setAmount(amount).build();

        AccountResponse response = grpcClient.syncInvoke(accountServiceBlockingStub, "payment",
                request, AccountResponse.class);

        return response.getResult();
    }

}

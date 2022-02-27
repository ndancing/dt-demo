package com.example.dt.demo.app.order.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.example.dt.demo.grpc.inventory.service.InventoryRequest;
import com.example.dt.demo.grpc.inventory.service.InventoryResponse;
import com.example.dt.demo.grpc.inventory.service.InventoryServiceGrpc;
import org.dromara.hmily.grpc.client.GrpcHmilyClient;
import org.dromara.hmily.grpc.filter.GrpcHmilyTransactionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InventoryGrpcClient {

    @Value("${client.grpc.inventory.host}")
    private String host;

    @Value("${client.grpc.inventory.port}")
    private int port;

    private InventoryServiceGrpc.InventoryServiceBlockingStub inventoryServiceBlockingStub;

    @Autowired
    GrpcHmilyClient grpcClient;

    @PostConstruct
    private void init() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(host, port)
                .intercept(new GrpcHmilyTransactionFilter()).usePlaintext().build();
        inventoryServiceBlockingStub = InventoryServiceGrpc.newBlockingStub(managedChannel);
    }

    public boolean decrease(String productId, Integer count) {
        InventoryRequest request = InventoryRequest.newBuilder().setProductId(productId).setCount(count).build();

        InventoryResponse response = grpcClient.syncInvoke(inventoryServiceBlockingStub, "decrease",
                request, InventoryResponse.class);

        return response.getResult();
    }

}

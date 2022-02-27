package com.example.dt.demo.app.inventory.controller;

import com.example.dt.demo.grpc.inventory.service.InventoryRequest;
import com.example.dt.demo.grpc.inventory.service.InventoryResponse;
import com.example.dt.demo.grpc.inventory.service.InventoryServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.dromara.hmily.common.exception.HmilyRuntimeException;
import com.example.dt.demo.core.inventory.dto.InventoryDTO;
import com.example.dt.demo.app.inventory.service.InventoryBizService;
import org.dromara.hmily.grpc.filter.GrpcHmilyServerFilter;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService(interceptors = {GrpcHmilyServerFilter.class})
public class InventoryGrpcController extends InventoryServiceGrpc.InventoryServiceImplBase {

    @Autowired
    InventoryBizService inventoryBizService;
    
    @Override
    public void decrease(InventoryRequest inventoryRequest, StreamObserver<InventoryResponse> responseObserver) {
        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setProductId(inventoryRequest.getProductId());
        inventoryDTO.setCount(inventoryRequest.getCount());
        try {
            InventoryResponse response = InventoryResponse.newBuilder().setResult(inventoryBizService.decrease(inventoryDTO)).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new HmilyRuntimeException());
        }
    }

}

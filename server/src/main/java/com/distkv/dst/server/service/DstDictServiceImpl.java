package com.distkv.dst.server.service;

import com.distkv.dst.common.RequestTypeEnum;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.DictProtocol;
import com.distkv.dst.rpc.service.DstDictService;
import com.distkv.dst.server.runtime.DstRuntime;
import java.util.concurrent.CompletableFuture;

public class DstDictServiceImpl implements DstDictService {


  public DstDictServiceImpl(DstRuntime runtime) {
    this.runtime = runtime;
  }

  private DstRuntime runtime;

  @Override
  public CompletableFuture<DictProtocol.PutResponse> put(
        DictProtocol.PutRequest request) {
    CompletableFuture<DictProtocol.PutResponse> future = new CompletableFuture<>();
    runtime.getWorkerPool().postRequest(
          request.getKey(), RequestTypeEnum.DICT_PUT, request, future);
    return future;
  }

  @Override
  public CompletableFuture<DictProtocol.GetResponse> get(
        DictProtocol.GetRequest request) {
    CompletableFuture<DictProtocol.GetResponse> future = new CompletableFuture<>();
    runtime.getWorkerPool().postRequest(
          request.getKey(), RequestTypeEnum.DICT_GET, request, future);
    return future;
  }

  @Override
  public CompletableFuture<DictProtocol.GetItemResponse> getItemValue(
        DictProtocol.GetItemRequest request) {
    CompletableFuture<DictProtocol.GetItemResponse> future = new CompletableFuture<>();
    runtime.getWorkerPool().postRequest(
          request.getKey(), RequestTypeEnum.DICT_GET_ITEM, request, future);
    return future;
  }

  @Override
  public CompletableFuture<DictProtocol.PopItemResponse> popItem(
        DictProtocol.PopItemRequest request) {
    CompletableFuture<DictProtocol.PopItemResponse> future = new CompletableFuture<>();
    runtime.getWorkerPool().postRequest(
          request.getKey(), RequestTypeEnum.DICT_POP_ITEM, request, future);
    return future;
  }

  @Override
  public CompletableFuture<DictProtocol.PutItemResponse> putItem(
        DictProtocol.PutItemRequest request) {
    CompletableFuture<DictProtocol.PutItemResponse> future = new CompletableFuture<>();
    runtime.getWorkerPool().postRequest(
          request.getKey(), RequestTypeEnum.DICT_PUT_ITEM, request, future);
    return future;
  }

  @Override
  public CompletableFuture<DictProtocol.RemoveItemResponse> removeItem(
        DictProtocol.RemoveItemRequest request) {
    CompletableFuture<DictProtocol.RemoveItemResponse> future = new CompletableFuture<>();
    runtime.getWorkerPool().postRequest(
          request.getKey(), RequestTypeEnum.DICT_REMOVE_ITEM, request, future);
    return future;
  }


  @Override
  public CompletableFuture<CommonProtocol.DropResponse> drop(
        CommonProtocol.DropRequest request) {
    CompletableFuture<CommonProtocol.DropResponse> future = new CompletableFuture<>();
    runtime.getWorkerPool().postRequest(
          request.getKey(), RequestTypeEnum.DICT_DROP, request, future);
    return future;
  }
}

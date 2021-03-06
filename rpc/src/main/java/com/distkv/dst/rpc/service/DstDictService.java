package com.distkv.dst.rpc.service;

import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.DictProtocol;

import java.util.concurrent.CompletableFuture;

public interface DstDictService {

  CompletableFuture<DictProtocol.PutResponse> put(DictProtocol.PutRequest request);

  CompletableFuture<DictProtocol.GetResponse> get(DictProtocol.GetRequest request);

  CompletableFuture<DictProtocol.GetItemResponse> getItemValue(DictProtocol.GetItemRequest request);

  CompletableFuture<DictProtocol.PopItemResponse> popItem(DictProtocol.PopItemRequest request);

  CompletableFuture<DictProtocol.PutItemResponse> putItem(DictProtocol.PutItemRequest request);

  CompletableFuture<CommonProtocol.DropResponse> drop(CommonProtocol.DropRequest request);

  CompletableFuture<DictProtocol.RemoveItemResponse> removeItem(
      DictProtocol.RemoveItemRequest request);
}

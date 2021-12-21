package com.xunle.rpc.client;

import com.xunle.rpc.entity.RpcRequest;

/**
 * @author xunle
 * @date 2021/12/21 12:40
 */
public interface RpcClient {

    Object sendRequest(RpcRequest request);
}

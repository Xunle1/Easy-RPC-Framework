package com.xunle.rpc.exception;

import com.xunle.rpc.enumeration.RpcError;

/**
 * RPC调用异常
 * @author xunle
 * @date 2021/12/17 16:16
 */
public class RpcException extends RuntimeException{

    public RpcException(RpcError error) {
        super(error.getMsg());
    }

    public RpcException(RpcError error, String msg) {
        super(error.getMsg() + ": " + msg);
    }

    public RpcException(String msg, Throwable cause) {
        super(msg, cause);
    }

}

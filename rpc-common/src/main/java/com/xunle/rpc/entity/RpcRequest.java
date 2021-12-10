package com.xunle.rpc.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 消费者向提供者发送的请求
 * @author xunle
 * @date 2021/12/9 23:55
 */
@Data
@Builder
public class RpcRequest implements Serializable {

    private String interfaceName;
    private String methodName;
    private Object[] parameters;
    private Class<?>[] paramTypes;
}

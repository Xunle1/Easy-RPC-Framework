package com.xunle.rpc.client;

import com.xunle.rpc.entity.RpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Rpc客户端代理
 * @author xunle
 * @date 2021/12/10 18:01
 */
public class RpcClientProxy implements InvocationHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcClientProxy.class);

    private RpcClient client;

    public RpcClientProxy(RpcClient client) {
        this.client = client;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz},this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        LOGGER.info("调用方法：{}#{}", method.getDeclaringClass().getName(), method.getName());
        RpcRequest request = new RpcRequest(method.getDeclaringClass().getName(),
                method.getName(),
                args,
                method.getParameterTypes());

        return client.sendRequest(request);
    }
}

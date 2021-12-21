package com.xunle.rpc.server;

import com.xunle.rpc.entity.RpcRequest;
import com.xunle.rpc.entity.RpcResponse;
import com.xunle.rpc.enumeration.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 实际工作的线程
 * @author xunle
 * @date 2021/12/10 17:50
 */
public class RpcRequestHandler{

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcRequestHandler.class);

    public Object handle(RpcRequest request, Object service) {
        Object result = null;
        try {
            result = invokeTargetMethod(request,service);
            LOGGER.info("服务:{} 成功调用方法:{}", request.getInterfaceName(), request.getMethodName());
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("调用或发送时出现错误：",e);
        }
        return result;
    }

    private Object invokeTargetMethod(RpcRequest request, Object service) throws IllegalAccessException, InvocationTargetException{
        Method method;

        try {
            method = service.getClass().getMethod(request.getMethodName(),request.getParamTypes());
        } catch (NoSuchMethodException e) {
            return RpcResponse.fail(ResponseCode.NOT_FOUND_METHOD);
        }
        return method.invoke(service,request.getParameters());
    }
}

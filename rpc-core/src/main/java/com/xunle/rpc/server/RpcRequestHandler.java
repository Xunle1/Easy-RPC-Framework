package com.xunle.rpc.server;

import com.xunle.rpc.entity.RpcRequest;
import com.xunle.rpc.entity.RpcResponse;
import com.xunle.rpc.enumeration.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * 实际工作的线程
 * @author xunle
 * @date 2021/12/10 17:50
 */
public class RpcRequestHandler implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(RpcRequestHandler.class);

    private Socket socket;
    private Object service;

    public RpcRequestHandler(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {

            RpcRequest rpcRequest = (RpcRequest)objectInputStream.readObject();
            Object result = invokeMethod(rpcRequest);
            objectOutputStream.writeObject(RpcResponse.success(result));
            objectOutputStream.flush();
        } catch (IOException  | ClassNotFoundException | IllegalAccessException | InvocationTargetException e) {
            logger.error("调用时或发送时发生错误：", e);
        }
    }

    public Object invokeMethod(RpcRequest rpcRequest) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException{
        Class<?> clazz = Class.forName(rpcRequest.getMethodName());
        if (!clazz.isAssignableFrom(service.getClass())) {
            return RpcResponse.fail(ResponseCode.NOT_FOUND_CLASS);
        }
        Method method;
        try {
            method = service.getClass().getMethod(rpcRequest.getMethodName(),rpcRequest.getParamTypes());
        } catch (NoSuchMethodException e) {
            return RpcResponse.fail(ResponseCode.NOT_FOUND_METHOD);
        }
        return method.invoke(service,rpcRequest.getParameters());
    }
}

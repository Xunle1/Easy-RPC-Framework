package com.xunle.rpc.server;

import com.xunle.rpc.entity.RpcRequest;
import com.xunle.rpc.entity.RpcResponse;
import com.xunle.rpc.registry.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * @author xunle
 * @date 2021/12/17 19:02
 */
public class RpcRequestHandlerThread implements Runnable{

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcRequestHandlerThread.class);

    private Socket socket;
    private ServiceRegistry serviceRegistry;
    private RpcRequestHandler handler;

    public RpcRequestHandlerThread(Socket socket, ServiceRegistry serviceRegistry, RpcRequestHandler handler) {
        this.socket = socket;
        this.serviceRegistry = serviceRegistry;
        this.handler = handler;
    }

    @Override
    public void run() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {

            RpcRequest request = (RpcRequest) objectInputStream.readObject();
            String interfaceName = request.getInterfaceName();
            Object service = serviceRegistry.getService(interfaceName);
            Object result = handler.handle(request, service);
            objectOutputStream.writeObject(RpcResponse.success(result));
            objectOutputStream.flush();
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.error("调用或发送时出现错误：",e);
        }
    }



}

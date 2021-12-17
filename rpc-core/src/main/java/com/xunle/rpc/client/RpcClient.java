package com.xunle.rpc.client;

import com.xunle.rpc.entity.RpcRequest;
import com.xunle.rpc.entity.RpcResponse;
import com.xunle.rpc.enumeration.ResponseCode;
import com.xunle.rpc.enumeration.RpcError;
import com.xunle.rpc.exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 消费者（客户端）
 * @author xunle
 * @date 2021/12/10 17:40
 */
public class RpcClient {
    private static final Logger logger = LoggerFactory.getLogger(RpcClient.class);

    public Object sendRequest(RpcRequest rpcRequest, String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();

            RpcResponse response = (RpcResponse) objectInputStream.readObject();
            if (response == null) {
                logger.error("服务调用失败，service: {}", rpcRequest.getInterfaceName());
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service: " + rpcRequest.getInterfaceName());
            }
            if (response.getCode() == null || response.getCode() != ResponseCode.SUCCESS.getCode()) {
                logger.error("调用服务失败，service: {}",rpcRequest.getInterfaceName());
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service: " + rpcRequest.getInterfaceName());
            }
            return response.getData();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("客户端连接出错：", e);
            throw new RpcException("服务调用失败：", e);
        }
    }
}

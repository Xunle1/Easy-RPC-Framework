package com.xunle.test.socket;

import com.xunle.rpc.api.HelloService;
import com.xunle.rpc.api.UserService;
import com.xunle.rpc.registry.DefaultServiceRegistry;
import com.xunle.rpc.registry.ServiceRegistry;
import com.xunle.rpc.server.socket.SocketRpcServer;
import com.xunle.test.HelloServiceImpl;
import com.xunle.test.UserServiceImpl;

/**
 * 测试服务端
 * @author xunle
 * @date 2021/12/10 18:15
 */
public class SocketServerTest {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        UserService userService = new UserServiceImpl();

        ServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(helloService);
        registry.register(userService);

        SocketRpcServer rpcServer = new SocketRpcServer(registry);
        rpcServer.start(8000);
    }
}

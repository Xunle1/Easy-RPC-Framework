package com.xunle.test.netty;

import com.xunle.rpc.api.HelloService;
import com.xunle.rpc.api.UserService;
import com.xunle.rpc.registry.DefaultServiceRegistry;
import com.xunle.rpc.registry.ServiceRegistry;
import com.xunle.rpc.server.netty.NettyServer;
import com.xunle.test.HelloServiceImpl;
import com.xunle.test.UserServiceImpl;

/**
 * @author xunle
 * @date 2021/12/21 16:43
 */
public class NettyServerTest {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        UserService userService = new UserServiceImpl();

        ServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(helloService);
        registry.register(userService);

        NettyServer server = new NettyServer();
        server.start(8000);
    }
}

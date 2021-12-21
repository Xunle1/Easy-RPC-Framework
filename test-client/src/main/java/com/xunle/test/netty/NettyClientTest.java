package com.xunle.test.netty;

import com.xunle.rpc.api.HelloObject;
import com.xunle.rpc.api.HelloService;
import com.xunle.rpc.api.User;
import com.xunle.rpc.api.UserService;
import com.xunle.rpc.client.RpcClient;
import com.xunle.rpc.client.RpcClientProxy;
import com.xunle.rpc.client.netty.NettyClient;

/**
 * @author xunle
 * @date 2021/12/21 16:44
 */
public class NettyClientTest {

    public static void main(String[] args) {
        RpcClient client = new NettyClient("127.0.0.1",8000);
        RpcClientProxy proxy = new RpcClientProxy(client);

        UserService userService = proxy.getProxy(UserService.class);
        HelloService helloService = proxy.getProxy(HelloService.class);

        userService.addUser(new User("77","xunle"));
        User user = userService.getUserInfo("77");
        System.out.println(user);

        System.out.println(helloService.hello(new HelloObject(101,"hello")));
    }
}

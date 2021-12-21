package com.xunle.test.socket;

import com.xunle.rpc.api.HelloObject;
import com.xunle.rpc.api.HelloService;
import com.xunle.rpc.api.User;
import com.xunle.rpc.api.UserService;
import com.xunle.rpc.client.RpcClient;
import com.xunle.rpc.client.RpcClientProxy;
import com.xunle.rpc.client.socket.SocketRpcClient;

/**
 * 客户端测试
 * @author xunle
 * @date 2021/12/10 18:09
 */
public class SocketClientTest {

    public static void main(String[] args) {
        RpcClient client = new SocketRpcClient("127.0.0.1",8000);
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12,"test rpc");

        String res = helloService.hello(object);
        System.out.println(res);

        UserService userService = proxy.getProxy(UserService.class);
        userService.addUser(new User("10","xunle"));

        User user = userService.getUserInfo("10");
        System.out.println("返回用户：" + user);
    }
}

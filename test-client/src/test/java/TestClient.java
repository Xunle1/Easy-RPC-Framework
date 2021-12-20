import com.xunle.rpc.api.HelloObject;
import com.xunle.rpc.api.HelloService;
import com.xunle.rpc.api.User;
import com.xunle.rpc.api.UserService;
import com.xunle.rpc.client.RpcClientProxy;

/**
 * 客户端测试
 * @author xunle
 * @date 2021/12/10 18:09
 */
public class TestClient {

    public static void main(String[] args) {
        RpcClientProxy proxy = new RpcClientProxy("127.0.0.1",8000);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12,"test rpc");

        UserService userService = proxy.getProxy(UserService.class);
        userService.addUser(new User("10","xunle"));
        User user = userService.getUserInfo("10");

        String res = helloService.hello(object);
        System.out.println(res);

        System.out.println("返回用户：" + user);
    }
}

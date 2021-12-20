import com.xunle.rpc.api.HelloService;
import com.xunle.rpc.api.UserService;
import com.xunle.rpc.registry.DefaultServiceRegistry;
import com.xunle.rpc.registry.ServiceRegistry;
import com.xunle.rpc.server.RpcServer;

/**
 * 测试服务端
 * @author xunle
 * @date 2021/12/10 18:15
 */
public class TestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        UserService userService = new UserServiceImpl();

        ServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(helloService);
        registry.register(userService);

        RpcServer rpcServer = new RpcServer(registry);
        rpcServer.start(8000);
    }
}

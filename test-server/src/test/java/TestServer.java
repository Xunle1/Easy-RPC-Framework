import com.xunle.rpc.api.HelloService;
import com.xunle.rpc.server.RpcServer;

/**
 * 测试服务端
 * @author xunle
 * @date 2021/12/10 18:15
 */
public class TestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        RpcServer rpcServer = new RpcServer();
        rpcServer.register(helloService,9000);
    }
}

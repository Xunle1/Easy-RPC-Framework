package com.xunle.rpc.server;

import com.xunle.rpc.registry.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * 提供者（服务端）
 * @author xunle
 * @date 2021/12/10 17:42
 */
public class RpcServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServer.class);

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 50;
    private static final int KEEP_ALIVE_TIME = 60;
    private static final int BLOCKING_QUEUE_CAPACITY = 100;
    private final ExecutorService threadPool;
    private final ServiceRegistry serviceRegistry;
    private RpcRequestHandler handler = new RpcRequestHandler();

    public RpcServer(ServiceRegistry registry) {
        this.serviceRegistry = registry;
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(BLOCKING_QUEUE_CAPACITY);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, workingQueue);
    }


    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            LOGGER.info("服务器启动: {}", serverSocket.getLocalSocketAddress());
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                LOGGER.info("客户端连接: {}:{}", socket.getInetAddress(), socket.getPort());
                threadPool.execute(new RpcRequestHandlerThread(socket,serviceRegistry,handler));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            LOGGER.error("服务器启动时有错误发生：",e);
        }
    }
}

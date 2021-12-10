package com.xunle.rpc.server;

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
    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);
    private final ExecutorService threadPool;

    public RpcServer() {
        int corePollSize = 5;
        int maximumPollSize = 50;
        long keepAliveTime = 60;
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(100);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        threadPool = new ThreadPoolExecutor(corePollSize, maximumPollSize, keepAliveTime, TimeUnit.SECONDS, workingQueue, threadFactory);
    }

    public void register(Object service, int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("正在启动服务...");
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                logger.info("客户端连接成功！IP地址为：" + socket.getInetAddress());
                threadPool.execute(new WorkThread(socket, service));
            }
        } catch (IOException e) {
            logger.error("连接时发生错误：", e);
        }
    }
}

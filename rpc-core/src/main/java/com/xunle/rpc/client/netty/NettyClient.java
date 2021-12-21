package com.xunle.rpc.client.netty;

import com.xunle.rpc.client.RpcClient;
import com.xunle.rpc.codec.CommonDecoder;
import com.xunle.rpc.codec.CommonEncoder;
import com.xunle.rpc.entity.RpcRequest;
import com.xunle.rpc.entity.RpcResponse;
import com.xunle.rpc.serializer.JsonSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xunle
 * @date 2021/12/21 13:02
 */
public class NettyClient implements RpcClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClient.class);

    private final String HOST;
    private final int PORT;
    private static final Bootstrap bootstrap;

    public NettyClient(String host, int port) {
        this.HOST = host;
        this.PORT = port;
    }

    static {
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new CommonDecoder());
                        pipeline.addLast(new CommonEncoder(new JsonSerializer()));
                        pipeline.addLast(new NettyClientHandler());
                    }
                });
    }


    @Override
    public Object sendRequest(RpcRequest request) {
        try {
            ChannelFuture future = bootstrap.connect(HOST, PORT).sync();
            LOGGER.info("客户端连接到服务器：{}:{}", HOST, PORT);
            Channel channel = future.channel();
            if (channel != null) {
                channel.writeAndFlush(request).addListener(future1 -> {
                    if (future1.isSuccess()) {
                        LOGGER.info(String.format("向服务端发送消息：%s",request));
                    } else {
                        LOGGER.error("发送消息时有错误发生：", future1.cause());
                    }
                });
                channel.closeFuture().sync();
                //阻塞获取返回结果
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
                RpcResponse response = channel.attr(key).get();
                return response.getData();
            }
        } catch (InterruptedException e) {
            LOGGER.error("客户端连接时有错误发生：", e);
        }
        return null;
    }
}

package com.xunle.rpc.server.netty;

import com.xunle.rpc.entity.RpcRequest;
import com.xunle.rpc.entity.RpcResponse;
import com.xunle.rpc.registry.DefaultServiceRegistry;
import com.xunle.rpc.registry.ServiceRegistry;
import com.xunle.rpc.server.RpcRequestHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xunle
 * @date 2021/12/21 16:16
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServerHandler.class);
    private static RpcRequestHandler requestHandler;
    private static ServiceRegistry serviceRegistry;

    static {
        requestHandler = new RpcRequestHandler();
        serviceRegistry = new DefaultServiceRegistry();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest request) throws Exception {
        try {
            LOGGER.info("服务器收到请求：{}", request);
            String interfaceName = request.getInterfaceName();
            //通过注册中心获取目标方法
            Object service = serviceRegistry.getService(interfaceName);
            //执行目标方法并且返回方法结果
            Object result = requestHandler.handle(request, service);
            //返回方法执行结果给客户端
            ChannelFuture future = channelHandlerContext.writeAndFlush(RpcResponse.success(result));
            future.addListener(ChannelFutureListener.CLOSE);
        } finally {
            ReferenceCountUtil.release(request);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("处理过程调用时发生错误:");
        cause.printStackTrace();
        ctx.close();
    }
}

package com.xunle.rpc.codec;

import com.xunle.rpc.entity.RpcRequest;
import com.xunle.rpc.enumeration.PackageType;
import com.xunle.rpc.serializer.CommonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码器
 * @author xunle
 * @date 2021/12/21 14:53
 */
public class CommonEncoder extends MessageToByteEncoder {

    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    private final CommonSerializer serializer;

    public CommonEncoder(CommonSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, ByteBuf byteBuf) throws Exception {
        //Magic Number
        byteBuf.writeInt(MAGIC_NUMBER);
        //PackageType
        if (object instanceof RpcRequest) {
            byteBuf.writeInt(PackageType.REQUEST_PACK.getCode());
        } else {
            byteBuf.writeInt(PackageType.RESPONSE_PACK.getCode());
        }
        //SerializerCode
        byteBuf.writeInt(serializer.getCode());
        byte[] bytes = serializer.serialize(object);
        //Data Length
        byteBuf.writeInt(bytes.length);
        //Data Bytes
        byteBuf.writeBytes(bytes);
    }
}

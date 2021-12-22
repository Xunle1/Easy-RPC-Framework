package com.xunle.rpc.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.xunle.rpc.entity.RpcRequest;
import com.xunle.rpc.entity.RpcResponse;
import com.xunle.rpc.enumeration.SerializerCode;
import com.xunle.rpc.exception.RpcException;
import com.xunle.rpc.exception.SerializeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author xunle
 * @date 2021/12/22 12:40
 */
public class KryoSerializer implements CommonSerializer{

    private static final Logger LOGGER = LoggerFactory.getLogger(KryoSerializer.class);

    private final ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.register(RpcRequest.class);
        kryo.register(RpcResponse.class);
        //是否关闭注册行为
        kryo.setReferences(true);
        //是否关闭循环引用
        kryo.setRegistrationRequired(false);
        return kryo;
    });

    @Override
    public byte[] serialize(Object object) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             Output output = new Output(byteArrayOutputStream)) {
            Kryo kryo = (Kryo)this.kryoThreadLocal.get();
            //序列化
            kryo.writeObject(output,object);
            this.kryoThreadLocal.remove();
            return output.toBytes();
        } catch (IOException e) {
            LOGGER.error("序列化时发生错误：{}",e);
            throw new SerializeException("序列化失败");
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             Input input = new Input(byteArrayInputStream)) {
            Kryo kryo = (Kryo)this.kryoThreadLocal.get();
            //反序列化
            T t = kryo.readObject(input, clazz);
            this.kryoThreadLocal.remove();
            return t;
        } catch (IOException e) {
            LOGGER.info("反序列化时发生错误：{}", e);
            throw new SerializeException("反序列化失败");
        }
    }

    @Override
    public int getCode() {
        return SerializerCode.valueOf("KRYO").getCode();
    }
}

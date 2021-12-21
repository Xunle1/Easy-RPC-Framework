package com.xunle.rpc.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xunle.rpc.entity.RpcRequest;
import com.xunle.rpc.enumeration.SerializerCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author xunle
 * @date 2021/12/21 14:59
 */
public class JsonSerializer implements CommonSerializer{

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonSerializer.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(Object object) {
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(object);
//            LOGGER.info("序列化结果: {}", objectMapper.readValue(bytes, object.getClass()));
            return bytes;
        } catch (IOException e) {
            LOGGER.error("json序列化时发生错误：{}", e.getMessage());
            e.printStackTrace();;
            return null;
        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        try {
            Object object = objectMapper.readValue(bytes, clazz);
            if (object instanceof RpcRequest) {
                object = handleRequest(object);
            }
//            LOGGER.info("反序列化结果：{}", object);
            return object;
        } catch (IOException e) {
            LOGGER.error("json反序列化时发生错误：{}",e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 由于 RpcRequest中有 Object数组，反序列化可能失败，故使用 ParamTypes获取实际类型，辅助序列化。
     * @param object
     * @return
     * @throws IOException
     */
    private Object handleRequest(Object object) throws IOException{
        RpcRequest request = (RpcRequest) object;
        Class<?>[] paramTypes = request.getParamTypes();
        Object[] parameters = request.getParameters();
        for (int i = 0; i < paramTypes.length; i++) {
            Class<?> clazz = paramTypes[i];
            if (!clazz.isAssignableFrom(parameters[i].getClass())) {
                byte[] bytes = objectMapper.writeValueAsBytes(parameters[i]);
                parameters[i] = objectMapper.readValue(bytes, clazz);
            }
        }
        return request;
    }

    @Override
    public int getCode() {
        return SerializerCode.valueOf("JSON").getCode();
    }
}

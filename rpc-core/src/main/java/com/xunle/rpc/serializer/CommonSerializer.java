package com.xunle.rpc.serializer;

/**
 * 序列化器接口
 * @author xunle
 * @date 2021/12/21 14:56
 */
public interface CommonSerializer {

    byte[] serialize(Object object);

    Object deserialize(byte[] bytes, Class<?> clazz);

    int getCode();

    static CommonSerializer getByCode(int code) {
        switch (code) {
            case 1:
                return new JsonSerializer();
            default:
                return null;
        }
    }
}

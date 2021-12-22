package com.xunle.rpc.enumeration;

import java.io.Serializable;

/**
 * 表示序列化和反序列化器
 * @author xunle
 * @date 2021/12/21 14:35
 */
public enum SerializerCode {

    KRYO(0),
    JSON(1);

    private final int code;

    SerializerCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

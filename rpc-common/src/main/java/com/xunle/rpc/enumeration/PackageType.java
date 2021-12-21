package com.xunle.rpc.enumeration;

/**
 * 识别包类型
 * @author xunle
 * @date 2021/12/21 14:25
 */
public enum PackageType {

    REQUEST_PACK(0),
    RESPONSE_PACK(1);

    private final int code;

    PackageType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

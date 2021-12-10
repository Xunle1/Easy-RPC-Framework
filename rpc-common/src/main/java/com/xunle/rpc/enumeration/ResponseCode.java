package com.xunle.rpc.enumeration;

/**
 * @author xunle
 * @date 2021/12/10 17:35
 */
public enum ResponseCode {

    SUCCESS(200, "调用方法成功"),
    FAIL(500, "调用方法失败"),
    NOT_FOUND_METHOD(500,"未找到指定方法"),
    NOT_FOUND_CLASS(500,"未找到指定类");

    private final int code;
    private final String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

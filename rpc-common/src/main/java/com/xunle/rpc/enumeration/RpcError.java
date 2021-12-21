package com.xunle.rpc.enumeration;

/**
 * @author xunle
 * @date 2021/12/11 19:54
 */
public enum RpcError {

    SERVICE_INVOCATION_FAILURE("服务调用出错"),
    SERVICE_CAN_NOT_BE_NULL("注册的服务不得为空"),
    SERVICE_NOT_FOUND("找不到对应的服务"),
    SERVICE_NOT_IMPLEMENT_ANY_INSTANCE("注册的服务未实现接口"),
    UNKNOWN_PROTOCOL("不识别的协议包"),
    UNKNOWN_SERIALIZER("不识别的(反)序列化器"),
    UNKNOWN_PACKAGE_TYPE("不识别的数据包类型");

    private final String msg;

    RpcError(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}

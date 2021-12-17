package com.xunle.rpc.registry;

/**
 * @author xunle
 * @date 2021/12/17 16:09
 */
public interface ServiceRegistry {

    <T> void register(T service);

    Object getService(String serviceName);
}

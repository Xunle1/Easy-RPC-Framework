package com.xunle.rpc.registry;

import com.xunle.rpc.enumeration.RpcError;
import com.xunle.rpc.exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xunle
 * @date 2021/12/17 16:10
 */
public class DefaultServiceRegistry implements ServiceRegistry{

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultServiceRegistry.class);

    //已注册服务表
    private final Map<String, Object> serviceMap = new ConcurrentHashMap<>();
    private final Set<String> registeredService = ConcurrentHashMap.newKeySet();

    @Override
    public <T> void register(T service) {
        String serviceName = service.getClass().getCanonicalName();

        if (registeredService.contains(serviceName)) return ;
        registeredService.add(serviceName);

        Class<?>[] interfaces = service.getClass().getInterfaces();
        if (interfaces.length == 0) {
            throw new RpcException(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INSTANCE);
        }
        for (Class<?> clazz : interfaces) {
            serviceMap.put(clazz.getCanonicalName(),service);
        }
        LOGGER.info("向接口：{} 注册服务：{}", interfaces, serviceName);
    }

    @Override
    public Object getService(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if (service == null) {
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
        return service;
    }
}

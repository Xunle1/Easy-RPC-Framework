package com.xunle.test;

import com.xunle.rpc.api.HelloObject;
import com.xunle.rpc.api.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xunle
 * @date 2021/12/10 18:15
 */
public class HelloServiceImpl implements HelloService {

    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String hello(HelloObject object) {
        logger.info("接收到：{}", object.getMessage());
        return "这是调用的返回值：, id=" + object.getId();
    }
}

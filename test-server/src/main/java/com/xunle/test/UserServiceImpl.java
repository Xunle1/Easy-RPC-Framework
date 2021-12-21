package com.xunle.test;

import com.xunle.rpc.api.User;
import com.xunle.rpc.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xunle
 * @date 2021/12/20 14:38
 */
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private static Map<String, User> users = new HashMap<>();

    @Override
    public User getUserInfo(String id) {
        LOGGER.info("查询id: {} 的用户信息", id);
        return users.get(id);
    }

    @Override
    public void addUser(User user) {
        LOGGER.info("添加用户: {}", user);
        users.put(user.getId(), user);
    }
}

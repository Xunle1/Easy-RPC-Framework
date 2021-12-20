package com.xunle.rpc.api;

/**
 * @author xunle
 * @date 2021/12/20 14:36
 */
public interface UserService {

    User getUserInfo(String id);

    void addUser(User user);
}

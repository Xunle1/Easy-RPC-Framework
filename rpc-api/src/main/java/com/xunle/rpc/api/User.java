package com.xunle.rpc.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xunle
 * @date 2021/12/20 14:37
 */
@Data
@AllArgsConstructor
public class User implements Serializable {

    private String id;
    private String name;
}

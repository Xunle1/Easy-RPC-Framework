package com.xunle.rpc.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xunle
 * @date 2021/12/9 23:53
 */
@Data
@AllArgsConstructor
public class HelloObject implements Serializable {

    private Integer id;
    private String message;
}

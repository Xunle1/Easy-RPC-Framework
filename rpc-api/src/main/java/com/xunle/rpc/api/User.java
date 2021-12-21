package com.xunle.rpc.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xunle
 * @date 2021/12/20 14:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private String id;
    private String name;
}

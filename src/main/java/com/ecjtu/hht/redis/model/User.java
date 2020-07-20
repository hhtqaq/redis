package com.ecjtu.hht.redis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author hht
 * @date 2020/7/14 17:15
 */
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 272878860314743240L;
    private String id;
    private String name;
}

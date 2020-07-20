package com.ecjtu.hht.redis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author hht
 * @date 2020/7/14 16:57
 */
@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Order implements Serializable {


    private static final long serialVersionUID = 5180856340569778523L;

    private String id;

    private String name;

    private BigDecimal price;

    private User user;
}

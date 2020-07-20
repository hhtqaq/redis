package com.tdcloud.access.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author TX
 * @Description: DPiKey信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DPiKeyPackage implements Serializable {

    private static final long serialVersionUID = -355989812708204292L;

    List<DPiKeyInfo> dPiKeyInfoList = new ArrayList<>();

    /**
     * Web Token有效时长，单位：分钟
     */
    private Integer webTokenValidTime;

    /**
     * APP Token有效时长，单位：分钟
     */
    private Integer appTokenValidTime;

    /**
     * PC Token有效时长，单位：分钟
     */
    private Integer pcTokenValidTime;

}

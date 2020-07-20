package com.tdcloud.access.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author TX
 * @Description: DPiKey信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DPiKeyInfo implements Serializable {
    private static final long serialVersionUID = 3898260867819763759L;
    /**
     * 动态口令初始秘钥
     */
    private String dpiKey;
    /**
     * 动态口令启用时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dpiKeyStartTime;
    /**
     * 根服务器公钥
     */
    private String publicKey;
    /**
     * 根服务器私钥
     */
    private String privateKey;
    /**
     * DPiKey数据库id
     */
    private long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastUpdateTime;
}

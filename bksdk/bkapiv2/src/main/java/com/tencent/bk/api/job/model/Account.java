package com.tencent.bk.api.job.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 作业主结构
 */
@Data
public class Account {

    /**
     * id
     */
    @JsonProperty("id")
    private long id;

    /**
     * 名称
     */
    @JsonProperty("account")
    private String account;

    /**
     * 创建人
     */
    @JsonProperty("creator")
    private String creator;

    /**
     * 操作系统
     */
    @JsonProperty("os")
    private String os;
    /**
     * 别名
     */
    @JsonProperty("alias")
    private String alias;

    /**
     *  bizId
     */
    @JsonProperty("bk_biz_id")
    private String bk_biz_id;
    /**
     * 脚本创建时间
     */
    @JsonProperty("create_time")
    private String createTime;

}
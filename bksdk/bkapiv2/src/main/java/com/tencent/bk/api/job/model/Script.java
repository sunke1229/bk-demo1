package com.tencent.bk.api.job.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 作业主结构
 */
@Data
public class Script {
    /**
     * id
     */
    @JsonProperty("id")
    private long id;

    /**
     * 名称
     */
    @JsonProperty("name")
    private String name;

    /**
     * 版本
     */
    @JsonProperty("version")
    private String version;

    /**
     * 标签
     */
    @JsonProperty("tag")
    private String tag;
    /**
     * 类型
     */
    @JsonProperty("type")
    private String type;

    /**
     * 脚本创建者
     */
    @JsonProperty("creator")
    private String creater;
    /**
     * 脚本创建时间
     */
    @JsonProperty("create_time")
    private String createTime;

    /**
     * 业务id
     */
    @JsonProperty("public")
    private Boolean isPublic;
    /**
     * 业务id
     */
    @JsonProperty("bk_biz_id")
    private int bkBizId;
    /**
     * 脚本的最后修改人
     */
    @JsonProperty("last_modify_user")
    private String lastModifyUser;
    /**
     * 脚本的最后修改时间
     */
    @JsonProperty("last_modify_time")
    private String lastModifyTime;

    /**
     * 内容
     */
    @JsonProperty("content")
    private String content;

}
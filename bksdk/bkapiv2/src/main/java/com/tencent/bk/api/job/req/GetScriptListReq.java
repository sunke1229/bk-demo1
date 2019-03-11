package com.tencent.bk.api.job.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tencent.bk.api.protocol.ApiReq;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetScriptListReq extends ApiReq {

    /**
     * 必填：业务ID
     */
    @JsonProperty("bk_biz_id")
    private int bkBizId;
    /**
     * 是否公共脚本。true:公共脚本；false:业务脚本。默认值为false
     */
    @JsonProperty("is_public")
    private Boolean isPublic;


    /**
     * 是否需要返回脚本内容。true:返回脚本内容；false：不返回脚本内容。默认为false。
     */
    @JsonProperty("return_script_content")
    private Boolean returnScriptContent;

    /**
     * 默认0表示从第1条记录开始返回
     */
    private int start;
    /**
     * 返回记录数量 不传此参数默认返回全部
     */
    private int length;

    /**
     * 脚本类型。0：所有脚本类型，1：shell，2：bat，3：perl，4：python，5：powershell，6：sql。默认值为0
     */
    @JsonProperty("script_type")
    private String scriptType;

    /**
     * 脚本名称，支持模糊查询
     */
    @JsonProperty("script_name")
    private String scriptName;
}

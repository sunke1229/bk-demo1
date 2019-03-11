package com.tencent.bk.api.job.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tencent.bk.api.protocol.ApiReq;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetScriptDetailReq extends ApiReq {
    /**
     * 必填：业务ID
     */
    @JsonProperty("bk_biz_id")
    private int bkBizId;
    /**
     * id
     */
    @JsonProperty("id")
    private int id;

}

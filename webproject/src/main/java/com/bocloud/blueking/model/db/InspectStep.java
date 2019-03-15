package com.bocloud.blueking.model.db;

import com.alibaba.fastjson.JSON;
import com.bocloud.blueking.model.bussiness.JobData;
import com.tencent.bk.utils.json.JsonUtil;
import lombok.Data;
import org.hibernate.annotations.Where;
import javax.persistence.*;

@Entity
@Data
@Table(name = "t_inspect_step")
@Where(clause = "is_deleted = 0")
public class InspectStep extends GenericEntity {
    @Column
    private Integer stepOrder;

    @Column
    private Long scriptId;

    @Column
    private String ipList;

    @Column
    private String param;

    @Column
    private Integer timeout;

    @Column
    private String account;

    //TODO 注意bizId

    public static InspectStep parse(JobData data){
        InspectStep step = new InspectStep();
        step.setScriptId(data.getReferenceId());
        step.setParam(data.getParam());
        step.setTimeout(data.getTimeout());
        step.setIpList(JsonUtil.toJson(data.getIpList()));
        step.setAccount(data.getAccount());
        return  step;
    }
}
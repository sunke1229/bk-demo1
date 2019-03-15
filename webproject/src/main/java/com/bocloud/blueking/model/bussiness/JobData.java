package com.bocloud.blueking.model.bussiness;


import com.tencent.bk.api.job.model.IP;
import lombok.Data;

import java.util.Collection;

@Data
public class JobData {
    private Integer type ; //引用类型   必须
    private Collection<IP> ipList;
    private String param;
    private Integer timeout;
    private Long referenceId ;//引用id   必须
    private String account ;
    private String name ;

    public enum  Type {
        //快速  常规巡检  定时巡检
        FAST_SCRIPT,FAST_TEMPLATE,ROUTINE ,TIMING
    }
}

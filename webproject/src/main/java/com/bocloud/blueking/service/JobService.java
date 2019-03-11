package com.bocloud.blueking.service;


import com.bocloud.blueking.common.exception.BusinessException;
import com.bocloud.blueking.dto.RespDto;
import com.bocloud.blueking.model.bussiness.JobData;
import com.bocloud.blueking.model.db.InspectRecord;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface JobService {
    RespDto<InspectRecord>  execute(JobData data,Long userId ,String userName,Long bizId, HttpServletRequest request) throws BusinessException;

    void synInspectRecord(List<InspectRecord> unfinishedRecord);

}

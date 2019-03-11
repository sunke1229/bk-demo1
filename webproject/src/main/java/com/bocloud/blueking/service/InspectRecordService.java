package com.bocloud.blueking.service;

import com.bocloud.blueking.dto.RespDto;
import com.bocloud.blueking.model.db.InspectRecord;


public interface InspectRecordService extends BaseCommonService<InspectRecord, Long> {
    RespDto updateStatus(Long  inspectRecordId, Integer status,Long userId);
   // Long executeJob(Long bizId , Long jobId, List<String> steps , List<String> globalVars);

}

package com.bocloud.blueking.schedule;

import com.bocloud.blueking.model.db.InspectRecord;
import com.bocloud.blueking.service.InspectRecordService;
import com.bocloud.blueking.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InspectRecordSchedule {
    private static Logger LOG = LoggerFactory.getLogger(InspectRecordSchedule.class);
    public final static long ONE_Minute =  60 * 1000;

    @Autowired
    InspectRecordService inspectRecordService;
    @Autowired
    JobService jobService;
    @Scheduled(fixedDelay=ONE_Minute*5)
    public void checkInspectRecordStatus(){
        List<InspectRecord> list =inspectRecordService.findAllRunning();
        for(InspectRecord inspectRecord:list){
            Long span = (System.currentTimeMillis() - inspectRecord.getCreateTime().getTime())/ONE_Minute*5;
            if(span>5){
                try{
                    jobService.synInspectRecord(inspectRecord);
                }catch (Exception e){
                    LOG.error("定时查询巡检结果异常",e);
                }
            }
        }
    }

}

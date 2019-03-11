package com.bocloud.blueking.service.impl;

import com.alibaba.fastjson.JSON;
import com.bocloud.blueking.common.exception.BusinessException;
import com.bocloud.blueking.dto.RespDto;
import com.bocloud.blueking.helper.RespHelper;
import com.bocloud.blueking.model.bussiness.JobData;
import com.bocloud.blueking.model.db.*;
import com.bocloud.blueking.service.InspectRecordService;
import com.bocloud.blueking.service.JobService;
import com.bocloud.blueking.service.RoutineInspectService;
import com.tencent.bk.api.job.JobApi;
import com.tencent.bk.api.job.model.IP;
import com.tencent.bk.api.job.model.JobInstanceResult;
import com.tencent.bk.api.job.model.JobInstanceStatus;
import com.tencent.bk.api.job.req.ExecuteJobReq;
import com.tencent.bk.api.job.req.FastExecuteScriptReq;
import com.tencent.bk.api.job.req.GetJobInstanceStatusReq;
import com.tencent.bk.api.protocol.ApiResp;
import com.tencent.bk.utils.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    InspectRecordService inspectRecordService;

    @Autowired
    JobApi jobApi;

    @Autowired
    RoutineInspectService routineInspectService;

    @Override
    public RespDto<InspectRecord> execute(JobData data, Long userId ,String userName, Long bizId, HttpServletRequest request) throws BusinessException {
        switch (JobData.Type.values()[data.getType()]){
            case FAST_TEMPLATE:
                throw  new BusinessException("暂不支持 快速巡检-模板");
               // return  executeTemplate(data,userId,userName,bizId,request);
            case FAST_SCRIPT:
                return  executeScript(data,userId,userName,bizId,request);
            case TIMING:
                throw  new BusinessException("暂不支持 定时巡检");
                //return executeTimingInspect(data,userId,userName,bizId,request);
            case ROUTINE:
                return executeRoutineInspect(data,userId,userName,bizId,request);
            default:
                break;
        }
        return RespHelper.fail(9999,"未知的任务类型");
    }

    @Override
    public void synInspectRecord(List<InspectRecord> unfinishedRecord) {
        for(InspectRecord inspectRecord:unfinishedRecord){
            InspectRecord record = inspectRecordService.get(inspectRecord.getId()).getData();
            Integer type = inspectRecord.getType();
            List<InspectRecordJobInstance> instances = record.getInspectRecordJobInstances();
            switch (JobData.Type.values()[type]){
                case FAST_TEMPLATE:
                    //TODO
                case FAST_SCRIPT:
                    InspectRecordJobInstance fastScriptInstance = instances.get(0);
                    Boolean isFinished = isFinished(fastScriptInstance.getId(),record.getBizId(),record.getUserName());
                    if(isFinished){
                        inspectRecordService.updateStatus(record.getId(),InspectRecord.Status.SUCCESS.ordinal(),User.SYSTEM_ID);
                    }
                case TIMING:
                    //TODO
                case ROUTINE:
                    //return executeRoutineInspect(data,userId,bizId,request);
                default:
                    break;

            }
        }
    }

    private RespDto<InspectRecord> executeTemplate(JobData data,Long userId,String userName,Long bizId, HttpServletRequest request){
        //TODO
        InspectRecord record = new InspectRecord();
        return inspectRecordService.save(record,userId);
    }

    private RespDto<InspectRecord> executeTimingInspect(JobData data,Long userId,String userName,Long bizId, HttpServletRequest request){
        //TODO
        InspectRecord record = new InspectRecord();
        return inspectRecordService.save(record,userId);
    }

    private RespDto<InspectRecord> executeRoutineInspect(JobData data,Long userId,String userName , Long bizId, HttpServletRequest request) throws BusinessException {
        RoutineInspect routineInspect = routineInspectService.get(data.getReferenceId()).getData();
        Integer type  = routineInspect.getType();
        Long instanceId ;
        if(type.equals(RoutineInspect.Type.STEP.ordinal())){
            InspectStep step = routineInspect.getInspectStep();
            FastExecuteScriptReq req =jobApi.makeBaseReqByWeb(FastExecuteScriptReq.class,request);
            req.setScriptId(step.getScriptId());
            req.setScriptParam(step.getParam());
            req.setScriptTimeout(step.getTimeout());
            req.setIpDtoList(JSON.parseArray(step.getIpList()).toJavaList(IP.class));
            req.setBkBizId(bizId.intValue());
            req.setAccount(step.getAccount());
            instanceId =  fastExecuteScript(req);
        }else if(type.equals(RoutineInspect.Type.TEMPLATE.ordinal())){
            //TODO
            throw  new BusinessException("暂不支持模板类型");
        } else{
            throw  new BusinessException("未知的类型");
        }

        InspectRecordJobInstance instance = new InspectRecordJobInstance();
        instance.setReferenceInstanceId(instanceId);
        instance.setInstanceContent(JsonUtil.toJson(data));
        InspectRecord record = new InspectRecord();
        record.setType(data.getType());
        record.setUserName(userName);
        List<InspectRecordJobInstance> list  = new ArrayList<>();
        list.add(instance);
        record.setInspectRecordJobInstances(list);
        return inspectRecordService.save(record,userId);
    }

    private RespDto<InspectRecord> executeScript(JobData data,Long userId,String userName ,Long bizId, HttpServletRequest request) throws BusinessException {
        FastExecuteScriptReq req =jobApi.makeBaseReqByWeb(FastExecuteScriptReq.class,request);
        req.setScriptId(data.getReferenceId());
        req.setScriptParam(data.getParam());
        req.setScriptTimeout(data.getTimeout());
        req.setIpDtoList(data.getIpList());
        req.setBkBizId(bizId.intValue());
        req.setAccount(data.getAccount());

        Long instanceId = fastExecuteScript(req);
        InspectRecordJobInstance instance = new InspectRecordJobInstance();
        instance.setReferenceInstanceId(instanceId);
        instance.setInstanceContent(JsonUtil.toJson(data));
        instance.setType(InspectRecordJobInstance.Type.SCRIPT.ordinal());

        InspectRecord record = new InspectRecord();
        record.setType(data.getType());
        record.setBizId(bizId);
        record.setUserName(userName);
        List<InspectRecordJobInstance> list  = new ArrayList<>();
        list.add(instance);
        record.setInspectRecordJobInstances(list);
        return inspectRecordService.save(record,userId);
    }


    private boolean isFinished(Long instanceId,Long bizId,String username){
        GetJobInstanceStatusReq req = jobApi.makeBaseReq(GetJobInstanceStatusReq.class,username);
        req.setId(instanceId);
        req.setBkBizId(bizId.intValue());
        ApiResp<JobInstanceStatus> resp = jobApi.getJobInstanceStatus(req);
        JobInstanceStatus status = resp.getData();
        return  status.isFinished();
    }



    private Long executeJob(ExecuteJobReq req) throws BusinessException {
        ApiResp<JobInstanceResult> resp = jobApi.executeJob(req);
        if(resp.getResult()){
            return resp.getData().getId();
        }
        throw new BusinessException("执行job失败",9999);
    }

    private Long fastExecuteScript(FastExecuteScriptReq req) throws BusinessException {
        ApiResp<JobInstanceResult> resp =  jobApi.fastExecuteScript(req);
        if(resp.getResult()){
            return resp.getData().getId();
        }
        throw new BusinessException("执行job失败",9999);
    }

}

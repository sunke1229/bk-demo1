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
import java.util.concurrent.ConcurrentHashMap;

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
    public void synInspectRecord(InspectRecord unfinishedRecord) {
        InspectRecord record = inspectRecordService.get(unfinishedRecord.getId()).getData();
        Integer type = unfinishedRecord.getType();
        List<InspectRecordJobInstance> instances = record.getInspectRecordJobInstances();
        Integer instanceStatus =0;
        switch (JobData.Type.values()[type]){
            case FAST_TEMPLATE:
                //TODO
                break;
            case FAST_SCRIPT:
                InspectRecordJobInstance fastScriptInstance = instances.get(0);
                instanceStatus = instanceStatus(fastScriptInstance.getReferenceInstanceId(),record.getBizId(),record.getUserName());
                break;
            case TIMING:
                //TODO
                break;
            case ROUTINE:
                for(InspectRecordJobInstance instance:instances){
                    instanceStatus = instanceStatus(instance.getReferenceInstanceId(),record.getBizId(),record.getUserName());
                    if(instanceStatus.equals(InspectRecord.Status.RUNNING.ordinal())){
                        break;
                    }
                }
                break;
            default:
                break;
        }
        if(!instanceStatus.equals(InspectRecord.Status.RUNNING.ordinal())){
            inspectRecordService.updateStatus(record.getId(),instanceStatus,User.SYSTEM_ID);
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
        String name ;
        InspectRecordJobInstance instance = new InspectRecordJobInstance();
        if(type.equals(RoutineInspect.Type.STEP.ordinal())){
            InspectStep step = routineInspect.getInspectStep();
            instance.setInstanceContent(JsonUtil.toJson(step));//***内部数据***
            FastExecuteScriptReq req =jobApi.makeBaseReqByWeb(FastExecuteScriptReq.class,request);
            req.setScriptId(step.getScriptId());
            req.setScriptParam(step.getParam());
            req.setScriptTimeout(step.getTimeout());
            req.setIpDtoList(JSON.parseArray(step.getIpList()).toJavaList(IP.class));
            req.setBkBizId(bizId.intValue());
            req.setAccount(step.getAccount());
            req.setCallBackUrl("http://10.20.50.167:8000/rest/inspect/callback");
            JobInstanceResult result = fastExecuteScript(req);
            instanceId =  result.getId();
            name = result.getName();
        }else if(type.equals(RoutineInspect.Type.TEMPLATE.ordinal())){
            //TODO
            throw  new BusinessException("暂不支持模板类型");
        } else{
            throw  new BusinessException("未知的类型");
        }
        instance.setReferenceInstanceId(instanceId);
        instance.setName(name);
        InspectRecord record = new InspectRecord();
        record.setType(data.getType());
        record.setUserName(userName);
        record.setName("常规巡检-"+System.currentTimeMillis());
        record.setBizId(bizId);
        List<InspectRecordJobInstance> list  = new ArrayList<>();
        list.add(instance);
        record.setInspectRecordJobInstances(list);
        return inspectRecordService.save(record,userId);
    }

    private RespDto<InspectRecord> executeScript(JobData data,Long userId,String userName ,Long bizId, HttpServletRequest request) throws BusinessException {
        FastExecuteScriptReq req =jobApi.makeBaseReqByWeb(FastExecuteScriptReq.class,request);

        InspectStep step = InspectStep.parse(data);

        req.setScriptId(step.getScriptId());
        req.setScriptParam(step.getParam());
        req.setScriptTimeout(step.getTimeout());
        req.setIpDtoList(JSON.parseArray(step.getIpList()).toJavaList(IP.class));
        req.setBkBizId(bizId.intValue());
        req.setAccount(step.getAccount());
        req.setCallBackUrl("http://10.20.50.167:8000/rest/inspect/callback");
        Long instanceId = fastExecuteScript(req).getId();
        InspectRecordJobInstance instance = new InspectRecordJobInstance();
        instance.setReferenceInstanceId(instanceId);
        instance.setInstanceContent(JsonUtil.toJson(step));
        instance.setType(InspectRecordJobInstance.Type.SCRIPT.ordinal());

        InspectRecord record = new InspectRecord();
        record.setName(data.getName());
        record.setType(data.getType());
        record.setBizId(bizId);
        record.setUserName(userName);
        List<InspectRecordJobInstance> list  = new ArrayList<>();
        list.add(instance);
        record.setInspectRecordJobInstances(list);
        return inspectRecordService.save(record,userId);
    }


    /**
     *
     * @param instanceId
     * @param bizId
     * @param username
     * @return 状态  0 进行中 ，1 成功 2 失败
     */
    private Integer instanceStatus(Long instanceId,Long bizId,String username){
        GetJobInstanceStatusReq req = jobApi.makeBaseReq(GetJobInstanceStatusReq.class,username);
        req.setId(instanceId);
        req.setBkBizId(bizId.intValue());
        ApiResp<JobInstanceStatus> resp = jobApi.getJobInstanceStatus(req);
        JobInstanceStatus status = resp.getData();
        if(!status.isFinished()){
            return  InspectRecord.Status.RUNNING.ordinal();
        }
        if(status.getJobInstance().getStatus()==3){
            return  InspectRecord.Status.SUCCESS.ordinal();
        }
        return  InspectRecord.Status.ERROR.ordinal();
    }



    private Long executeJob(ExecuteJobReq req) throws BusinessException {
        ApiResp<JobInstanceResult> resp = jobApi.executeJob(req);
        if(resp.getResult()){
            return resp.getData().getId();
        }
        throw new BusinessException("执行job失败",9999);
    }

    private JobInstanceResult fastExecuteScript(FastExecuteScriptReq req) throws BusinessException {
        ApiResp<JobInstanceResult> resp =  jobApi.fastExecuteScript(req);
        if(resp.getResult()){
            return resp.getData();
        }
        throw new BusinessException("执行job失败",9999);
    }

}
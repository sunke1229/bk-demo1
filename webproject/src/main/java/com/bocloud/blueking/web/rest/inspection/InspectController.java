package com.bocloud.blueking.web.rest.inspection;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bocloud.blueking.common.exception.BusinessException;
import com.bocloud.blueking.dto.RespDto;
import com.bocloud.blueking.helper.RespHelper;
import com.bocloud.blueking.model.bussiness.JobData;
import com.bocloud.blueking.model.db.InspectRecord;
import com.bocloud.blueking.model.db.User;
import com.bocloud.blueking.service.InspectRecordService;
import com.bocloud.blueking.service.JobService;
import com.bocloud.blueking.web.BaseController;
import com.tencent.bk.api.cc.CCApi;
import com.tencent.bk.api.cc.model.CommonSearchDataList;
import com.tencent.bk.api.cc.model.Page;
import com.tencent.bk.api.cc.req.SearchHostReq;
import com.tencent.bk.api.job.JobApi;
import com.tencent.bk.api.job.model.StepInstanceAnalysis;
import com.tencent.bk.api.job.req.*;
import com.tencent.bk.api.protocol.ApiResp;
import com.tencent.bk.utils.json.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Controller
public class InspectController extends BaseController {
    private static Logger LOG = LoggerFactory.getLogger(InspectController.class);
    @Autowired
    JobService  jobService;

    @Autowired
    InspectRecordService inspectRecordService;

    @Autowired
    JobApi jobApi;

    @Autowired
    CCApi ccApi;
    @RequestMapping(value ="/inspect/execute",method ={RequestMethod.POST})
    @ResponseBody
    public String execute(@RequestBody JobData jobData)  {
        try {
            User user = getLocalUser();
            if(user==null){
                return  JsonUtil.toJson(RespHelper.fail(9999,"用户信息不存在"));
            }
            RespDto<InspectRecord> respDto =  jobService.execute(jobData,user.getId(),user.getUsername(),user.getBizId(),request);
            return JsonUtil.toJson(respDto);
        } catch (BusinessException e) {
            return JsonUtil.toJson(RespHelper.fail(e.getCode(),e.getMessage()));
        }
    }


    @RequestMapping(value ="/inspect/job/list",method ={RequestMethod.GET})
    @ResponseBody
    public String jobList() {
        GetJobListReq req = jobApi.makeBaseReqByWeb(GetJobListReq.class,request);
        User user = getLocalUser();
        req.setBkBizId(user.getBizId().intValue());
        return JsonUtil.toJson(jobApi.getJobList(req));
    }


    @RequestMapping(value ="/inspect/account/list",method ={RequestMethod.GET})
    @ResponseBody
    public String accountList()  {
        GetAccountListReq req = jobApi.makeBaseReqByWeb(GetAccountListReq.class,request);
        User user = getLocalUser();
        req.setBkBizId(user.getBizId().intValue());
        return JsonUtil.toJson(jobApi.getAccountList(req));
    }

    @RequestMapping(value ="/inspect/script/list",method ={RequestMethod.GET})
    @ResponseBody
    public String scriptList()  {
        GetScriptListReq req = jobApi.makeBaseReqByWeb(GetScriptListReq.class,request);
        User user = getLocalUser();
        req.setBkBizId(user.getBizId().intValue());
        return JsonUtil.toJson(jobApi.getScriptList(req));
    }

    @RequestMapping(value ="/inspect/script/detail/{id}",method ={RequestMethod.GET})
    @ResponseBody
    public String scriptDetail(@PathVariable("id") Integer id) {
        GetScriptDetailReq req = jobApi.makeBaseReqByWeb(GetScriptDetailReq.class,request);
        User user = getLocalUser();
        req.setBkBizId(user.getBizId().intValue());
        req.setId(id);
        return JsonUtil.toJson(jobApi.getScriptDetail(req));
    }


    @RequestMapping(value ="/inspect/host/list",method ={RequestMethod.GET})
    @ResponseBody
    public String hostList(Integer start ,Integer length) {
        SearchHostReq req = jobApi.makeBaseReqByWeb(SearchHostReq.class,request);
        Page page = new Page();
        page.setLimit(length);
        page.setStart(start);
        User user = getLocalUser();
        req.setBkBizId(user.getBizId().intValue());
        req.setPage(page);
        ApiResp<CommonSearchDataList> apiResp = ccApi.searchHost(req);
        return JsonUtil.toJson(apiResp);
    }

    @RequestMapping(value ="/inspect/job/{id}/log",method ={RequestMethod.GET})
    @ResponseBody
    public String inspectLog(@PathVariable("id") Integer id) {
        GetJobInstanceLogReq req = jobApi.makeBaseReqByWeb(GetJobInstanceLogReq.class,request);
        User user = getLocalUser();
        req.setBkBizId(user.getBizId().intValue());
        req.setId(id);
        ApiResp<List<StepInstanceAnalysis>> resp = jobApi.getJobInstanceLog(req);
        return JsonUtil.toJson(resp);
    }

    @RequestMapping(value="/inspect/callback" ,produces="application/x-www-form-urlencoded;charset=UTF-8",method ={RequestMethod.POST})
    @ResponseBody
    public String  callback( @RequestParam Map<String,Object> map) {
        String json = "{}";
        for(String key : map.keySet()){
            json = key;
            break;
        }
        JSONObject data = JSON.parseObject(json);
        Long instanceId = data.getLong("job_instance_id");
        Integer status = data.getInteger("status");

        inspectRecordService.synInstance(instanceId,status);
        LOG.info("callback:{}",json);
        return "---";
    }



}

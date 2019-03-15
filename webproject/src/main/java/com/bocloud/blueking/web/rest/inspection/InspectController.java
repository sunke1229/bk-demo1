package com.bocloud.blueking.web.rest.inspection;


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
import com.tencent.bk.api.job.req.GetJobInstanceLogReq;
import com.tencent.bk.api.job.req.GetJobListReq;
import com.tencent.bk.api.job.req.GetScriptDetailReq;
import com.tencent.bk.api.job.req.GetScriptListReq;
import com.tencent.bk.api.protocol.ApiResp;
import com.tencent.bk.utils.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class InspectController extends BaseController {

    @Autowired
    JobService  jobService;

    @Autowired
    InspectRecordService inspectRecordService;

    @Autowired
    JobApi jobApi;

    @Autowired
    CCApi ccApi;
    @RequestMapping("/inspect/execute")
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


    @RequestMapping("/inspect/job/list")
    @ResponseBody
    public String jobList() {
        GetJobListReq req = jobApi.makeBaseReqByWeb(GetJobListReq.class,request);
        User user = getLocalUser();
        req.setBkBizId(user.getBizId().intValue());
        return JsonUtil.toJson(jobApi.getJobList(req));
    }

    @RequestMapping("/inspect/script/list")
    @ResponseBody
    public String scriptList() {
        GetScriptListReq req = jobApi.makeBaseReqByWeb(GetScriptListReq.class,request);
        User user = getLocalUser();
        req.setBkBizId(user.getBizId().intValue());
        return JsonUtil.toJson(jobApi.getScriptList(req));
    }

    @RequestMapping("/inspect/script/detail/{id}")
    @ResponseBody
    public String scriptDetail(@PathVariable("id") Integer id) {
        GetScriptDetailReq req = jobApi.makeBaseReqByWeb(GetScriptDetailReq.class,request);
        User user = getLocalUser();
        req.setBkBizId(user.getBizId().intValue());
        req.setId(id);
        return JsonUtil.toJson(jobApi.getScriptDetail(req));
    }


    @RequestMapping("/inspect/host/list")
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

    @RequestMapping("/inspect/job/{id}/log")
    @ResponseBody
    public String inspectLog(@PathVariable("id") Integer id) {
        GetJobInstanceLogReq req = jobApi.makeBaseReqByWeb(GetJobInstanceLogReq.class,request);
        User user = getLocalUser();
        req.setBkBizId(user.getBizId().intValue());
        req.setId(id);
        ApiResp<List<StepInstanceAnalysis>> resp = jobApi.getJobInstanceLog(req);
        return JsonUtil.toJson(resp);
    }

    @RequestMapping("/inspect/callback")
    @ResponseBody
    public String callback(@RequestBody JobData jobData) {
        System.out.println(JsonUtil.toJson(jobData));
        return "---";
    }


    @RequestMapping("/inspect/aaaa/test")
    @ResponseBody
    public String test() throws BusinessException {
        Pageable pageable = checkPageAndSize(0,10);
        List<InspectRecord> list =  inspectRecordService.findAll(pageable).getData().getContent();
        jobService.synInspectRecord(list.get(0));
        return  "end";
    }

}

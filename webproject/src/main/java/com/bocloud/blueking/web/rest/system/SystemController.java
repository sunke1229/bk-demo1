/*
 *  Copyright (c) 2017 . Tencent 蓝鲸智云(BlueKing)
 */

package com.bocloud.blueking.web.rest.system;

import com.bocloud.blueking.dto.RespDto;
import com.bocloud.blueking.helper.RespHelper;
import com.bocloud.blueking.model.db.User;
import com.bocloud.blueking.service.UserService;
import com.bocloud.blueking.web.BaseController;
import com.tencent.bk.api.cc.CCApi;
import com.tencent.bk.api.cc.model.CommonSearchDataList;
import com.tencent.bk.api.cc.req.SearchBusinessReq;
import com.tencent.bk.api.protocol.ApiResp;
import com.tencent.bk.utils.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


/**
 *
 */
@Controller
public class SystemController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    CCApi ccApi;

    @RequestMapping(value = "/system/biz/list",method ={RequestMethod.GET})
    @ResponseBody
    public String listBiz() {
        SearchBusinessReq searchBusinessReq = ccApi.makeBaseReqByWeb(SearchBusinessReq.class,request);
        ApiResp<CommonSearchDataList> resp =  ccApi.searchBusiness(searchBusinessReq);
        return JsonUtil.toJson(RespHelper.ok(resp.getData()));
    }


    @RequestMapping(value = "/system/changeBiz",method ={RequestMethod.POST})
    @ResponseBody
    public String changeBiz(Long bizId) {
        SearchBusinessReq searchBusinessReq = ccApi.makeBaseReqByWeb(SearchBusinessReq.class,request);
        Map<String,Object> condition =  new HashMap<>();
        condition.put("bk_biz_id",bizId);
        searchBusinessReq.setCondition(condition);
        ApiResp<CommonSearchDataList> resp =  ccApi.searchBusiness(searchBusinessReq);
        Map bizData = resp.getData().getInfo().get(0);
        String bizName = (String)bizData.get("bk_biz_name");
        User user =  getLocalUser();
        RespDto<Long> respDto =  userService.changeBiz(user.getId(),bizId,bizName);
        session.setAttribute("userName",user.getChname());
        session.setAttribute("businessName",bizName);
        return JsonUtil.toJson(respDto);
    }
}

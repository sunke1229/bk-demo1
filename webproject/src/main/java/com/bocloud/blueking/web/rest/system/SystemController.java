/*
 *  Copyright (c) 2017 . Tencent 蓝鲸智云(BlueKing)
 */

package com.bocloud.blueking.web.rest.system;

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
import org.springframework.web.bind.annotation.ResponseBody;


/**
 *
 */
@Controller
public class SystemController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    CCApi ccApi;

    @RequestMapping(value = "/system/biz/list")
    @ResponseBody
    public String listBiz() {
        SearchBusinessReq searchBusinessReq = ccApi.makeBaseReqByWeb(SearchBusinessReq.class,request);
        ApiResp<CommonSearchDataList> resp =  ccApi.searchBusiness(searchBusinessReq);
        return JsonUtil.toJson(resp.getData());
    }


    @RequestMapping(value = "/system/changeBiz")
    @ResponseBody
    public String changeBiz(Long bizId) {
        return JsonUtil.toJson(userService.changeBiz(getLocalUserId(),bizId));
    }
}

/*
 *  Copyright (c) 2017 . Tencent 蓝鲸智云(BlueKing)
 */

package com.bocloud.blueking.web.rest.system;

import com.bocloud.blueking.helper.RespHelper;
import com.bocloud.blueking.web.BaseController;
import com.google.common.collect.Maps;
import com.tencent.bk.api.login.BKLoginApi;
import com.tencent.bk.api.login.model.BkUser;
import com.tencent.bk.utils.bean.BeanUtil;
import com.tencent.bk.utils.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 *
 */
@Controller
public class LoginController extends BaseController {

    private final BKLoginApi bkLoginApi;

    @Autowired
    public LoginController( BKLoginApi bkLoginApi) {
        this.bkLoginApi = bkLoginApi;
    }


    @RequestMapping(value = "/user/info/")
    @ResponseBody
    public Object info() {
        BkUser user = getUser();
        Map<String, Object> map = Maps.newHashMap();
        BeanUtil.copy(user, map);
        //map.put("demo", demoService.getDemo()); //只是演示如何利用配置项而已
        return map;
    }

    @RequestMapping(value = "/user/logout")
    public String logout() {
        //这里可以做业务想做的注销掉用户其他信息的操作
        //会话失效
        session.invalidate();
        return "redirect:" + bkLoginApi.getLoginUrl(); //跳到登录页面
    }
}

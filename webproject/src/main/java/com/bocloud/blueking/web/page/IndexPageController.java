/*
 *  Copyright (c) 2017.  Tencent 蓝鲸智云(BlueKing)
 */

package com.bocloud.blueking.web.page;

import com.bocloud.blueking.dto.RespDto;
import com.bocloud.blueking.model.db.User;
import com.bocloud.blueking.service.UserService;
import com.bocloud.blueking.web.BaseController;
import com.tencent.bk.api.cc.CCApi;
import com.tencent.bk.api.cc.model.CommonSearchDataList;
import com.tencent.bk.api.cc.req.SearchBusinessReq;
import com.tencent.bk.api.login.model.BkUser;
import com.tencent.bk.api.protocol.ApiResp;
import com.tencent.bk.core.BkConsts;
import com.tencent.bk.sdk.web.filter.util.FilterUtil;
import com.tencent.bk.utils.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * 演示如何处理一个带页面的
 */
@Controller
@RequestMapping("/")
public class IndexPageController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    CCApi ccApi;


    @RequestMapping("/")
    public ModelAndView index(ModelAndView view) {

        String lang = FilterUtil.getSessionAttribute(session, BkConsts.SYS_LANGUAGE, String.class);
        if (lang == null) {
            BkUser user = getUser();
            if (user == null) {
                request.setAttribute("message", "没有登录态！");
                view.setViewName("/error/401");
                return view;
            }

            User userDto = BeanUtil.map(user, User.class);//session中的
            User data =  userService.findByUsername(user.getUsername());//数据库的
            if (data != null) {//更新
                userDto.setId(data.getId());
                userDto.setLang(data.getLang());
                userDto.setCreateTime(data.getCreateTime());
                userDto.setBizId(data.getBizId());
            }
            if (userDto.getLang() == null) {
                userDto.setLang("zh_CN");
            }
            if(userDto.getBizId()==null){
                SearchBusinessReq searchBusinessReq = ccApi.makeBaseReqByWeb(SearchBusinessReq.class,request);
                ApiResp<CommonSearchDataList> resp =  ccApi.searchBusiness(searchBusinessReq);
                CommonSearchDataList list = resp.getData();
                if(list!=null&&list.getCount()>0){
                    Integer bizId = (Integer)list.getInfo().get(0).get("bk_biz_id");
                    userDto.setBizId(bizId.longValue());
                }
            }
            userDto.setLastLoginTime(new Date());
            RespDto<User> saveRsp = userService.save(userDto);
            if (saveRsp.getData() == null) {
                request.setAttribute("message", "用户初始化错误！");
                view.setViewName("/error");
                return view;
            }
            lang = saveRsp.getData().getLang();
            session.setAttribute(BkConsts.SYS_LANGUAGE, lang);
        }
        view.setViewName("/index"); //首页
        return view;
    }

}

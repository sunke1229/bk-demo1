/*
 *  Copyright (c) 2017.  Tencent 蓝鲸智云(BlueKing)
 */

package com.bocloud.blueking.web;

import com.bocloud.blueking.common.exception.BusinessException;
import com.bocloud.blueking.helper.RespHelper;
import com.bocloud.blueking.model.db.User;
import com.bocloud.blueking.service.UserService;
import com.tencent.bk.api.login.model.BkUser;
import com.tencent.bk.core.BkConsts;
import com.tencent.bk.sdk.web.filter.util.FilterUtil;
import com.tencent.bk.utils.http.CookieUtil;
import com.tencent.bk.utils.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 所有Restful接口都要继承这个父类，保证rest的路径是以/rest开头
 */
@RequestMapping("/rest")
@SuppressWarnings({"unused"})
public abstract class BaseController {

    @Autowired
    protected HttpSession session;


    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected UserService userService;


    /**
     * 读取 当前用户
     *
     * @return
     */
    protected BkUser getUser() {
        return FilterUtil.getSessionAttribute(session, BkConsts.USER_SESSION, BkUser.class);
    }

    /**
     * 读取 当前用户
     *
     * @return
     */
    protected Long getLocalUserId() {
        BkUser bkUser = FilterUtil.getSessionAttribute(session, BkConsts.USER_SESSION, BkUser.class);
        return  userService.findIdByUsername(bkUser.getUsername());
    }
    /**
     * 读取 当前用户
     *
     * @return
     */
    protected User getLocalUser() {
        BkUser bkUser = FilterUtil.getSessionAttribute(session, BkConsts.USER_SESSION, BkUser.class);
        return  userService.findByUsername(bkUser.getUsername());
    }


    /**
     * 取当前用户登录态token
     *
     * @return
     */
    protected String getBkToken() {
        return CookieUtil.getCookieValue(request, BkConsts.C_BK_TOKEN);
    }

    /**
     * 取用户设置的语言
     *
     * @return string
     */
    protected String getUserLang() {
        return FilterUtil.getSessionAttribute(session, BkConsts.SYS_LANGUAGE, String.class);
    }


    protected Pageable checkPageAndSize(Integer page ,Integer size) throws BusinessException {
        page = (page==null?0:page);
        size = (size==null?10:size);
        if(page<0){
            throw new BusinessException("page应大于等于0", 9999);
        }
        if(size<1){
            throw new BusinessException("size应大于0", 9999);
        }
        return   new PageRequest(page,size);
    }

}

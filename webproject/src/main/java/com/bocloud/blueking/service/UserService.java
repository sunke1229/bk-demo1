/*
 *  Copyright (c) 2017.  Tencent 蓝鲸智云(BlueKing)
 */

package com.bocloud.blueking.service;

import com.bocloud.blueking.common.aop.annotation.ServiceAop;
import com.bocloud.blueking.dto.RespDto;
import com.bocloud.blueking.model.db.User;
import com.bocloud.blueking.serviceDemo.BaseCommonService;

import java.util.List;

/**
 *
 */
public interface UserService extends BaseCommonService<User, Long> {

    @ServiceAop
    User findByUsername(String username);

    Long findIdByUsername(String username);



    RespDto<List<User>> findByChname(String chname);

    RespDto<Long> changeBiz(Long userId , Long bizId);
}

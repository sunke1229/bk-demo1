/*
 *  Copyright (c) 2017.  Tencent 蓝鲸智云(BlueKing)
 */

package com.bocloud.blueking.demo.service;

import com.bocloud.blueking.common.aop.annotation.ServiceAop;
import com.bocloud.blueking.demo.model.Book;
import com.bocloud.blueking.dto.RespDto;
import com.bocloud.blueking.serviceDemo.BaseCommonService;

import java.util.List;

/**
 *
 */
public interface BookService extends BaseCommonService<Book, Integer> {

    @ServiceAop
    RespDto<Book> findByName(String name);

    RespDto<List<Book>> findByLikeName(String name);

    RespDto<List<Book>> deteleByBooks(List<Book> books);

    RespDto<List<Book>> findbooksByNameAndID(Integer id, String name);


}

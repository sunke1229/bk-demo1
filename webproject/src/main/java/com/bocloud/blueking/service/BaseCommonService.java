/*
 *  Copyright (c) 2017.  Tencent 蓝鲸智云(BlueKing)
 */

package com.bocloud.blueking.service;

import com.bocloud.blueking.dto.RespDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;

/**
 *
 */
public interface BaseCommonService<T, ID extends Serializable> {

    RespDto<T> get(ID id);

    RespDto<Page<T>> findAll(Pageable pageable);

    RespDto<Page<T>> findAll(Specification<T> spec, Pageable pageable);

    RespDto<T> save(T userDto,Long userId);

    RespDto<T> update(T userDto,Long userId);

    RespDto<ID> delete(ID id,Long userId);

}

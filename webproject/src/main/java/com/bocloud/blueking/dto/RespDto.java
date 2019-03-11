/*
 *  Copyright (c) 2017 . Tencent 蓝鲸智云(BlueKing)
 */

package com.bocloud.blueking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * 返回给请求方的响应通用结构DataTransferObject(DTO)
 */
@Getter
@Setter
public class RespDto<T> {
    /**
     * 错误码
     */
    public Integer code;
    /**
     * 错误描述
     */
    public String message;


    public Boolean success;
    /**
     * 此项当有值时才会返回
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
}
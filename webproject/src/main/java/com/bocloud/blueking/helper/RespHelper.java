/*
 *  Copyright (c) 2017.  Tencent 蓝鲸智云(BlueKing)
 */

package com.bocloud.blueking.helper;

import com.bocloud.blueking.dto.RespDto;

/**
 *
 */
public class RespHelper {

    public static <T> RespDto<T> createEmptyRespDto() {
        return new RespDto<>();
    }


    public static <T> RespDto<T> fail(int code, String message) {
        RespDto<T> t = createEmptyRespDto();
        t.setCode(code);
        t.setSuccess(false);
        t.setMessage(message);
        return t;
    }

    public static <T> RespDto<T> ok(T data) {
        RespDto<T> t = createEmptyRespDto();
        t.setSuccess(true);
        t.setData(data);
        return t;
    }
    public static <T> RespDto<T> ok() {
        RespDto<T> t = createEmptyRespDto();
        t.setSuccess(true);
        return t;
    }
}

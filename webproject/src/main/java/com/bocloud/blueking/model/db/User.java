/*
 *  Copyright (c) 2017.  Tencent 蓝鲸智云(BlueKing)
 */

package com.bocloud.blueking.model.db;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * User表 ORM对象
 */
@Data
@Entity
@Table(name="user")
public class User implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String chname;
    private Date createTime;
    private Date lastLoginTime;
    private String lang;
    private String phone;
    private String email;
    private Long bizId;

    public static   Long  SYSTEM_ID  = 0L ;

}

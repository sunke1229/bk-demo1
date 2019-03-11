package com.bocloud.blueking.model.db;


import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * 定义通用字段  被其他数据库表引用
 */
@Data
@MappedSuperclass//表示父类字段可被继承入库
public class GenericEntity implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private Date createTime;

    @Column
    private Date modifyTime;

    @Column
    private Long createrId;

    @Column
    private Long modifierId;

    @Column
    private Boolean isDeleted = false;

    public  void createNow(Long createrId){
        this.createrId = createrId;
        this.modifierId = createrId;
        this.createTime = new Date();
        this.modifyTime = this.createTime;
    }

    public  void modifyNow(Long modifierId){
        this.modifierId = modifierId;
        this.modifyTime = new Date();
    }

}

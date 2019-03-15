package com.bocloud.blueking.model.db;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date modifyTime;

    @Column
    private Long creatorId;

    @Column
    private Long modifierId;

    @Column
    private Boolean isDeleted = false;

    public  void createNow(Long creatorId){
        this.creatorId = creatorId;
        this.modifierId = creatorId;
        this.createTime = new Date();
        this.modifyTime = this.createTime;
    }

    public  void modifyNow(Long modifierId){
        this.modifierId = modifierId;
        this.modifyTime = new Date();
    }

}

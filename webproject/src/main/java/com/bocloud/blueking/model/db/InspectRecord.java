package com.bocloud.blueking.model.db;


import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Data
@Table(name = "t_inspect_record")
@Where(clause = "is_deleted = 0")
public class InspectRecord extends GenericEntity {

    @Column
    private Integer type;

    @Column
    private Integer status = Status.CREATED.ordinal();
    public enum Status {
        //自定义 ， 引用
        CREATED , RUNNING ,SUCCESS,ERROR
    }

    @Transient
    private List<InspectRecordJobInstance> inspectRecordJobInstances;

    @Column
    private String result;

    @Column
    private Long bizId;

    @Column
    private String userName;

}

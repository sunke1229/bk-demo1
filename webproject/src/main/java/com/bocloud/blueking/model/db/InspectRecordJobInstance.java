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
@Table(name = "t_inspect_record_job_instance")
@Where(clause = "is_deleted = 0")
public class InspectRecordJobInstance extends GenericEntity {

    @Column
    private Long inspectRecordId;

    @Column
    private Integer type ;
    public enum Type {

        JOB(0) , SCRIPT(1) ;
        int type ;
        Type(Integer type){
            this.type = type;
        }
    }

    @Column
    private Long referenceInstanceId;

    @Column(columnDefinition="TEXT")
    private String instanceContent;


    //TODO 注意bizId
}

package com.bocloud.blueking.model.db;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * 常规巡检
 */
@Entity
@Data
@Table(name = "t_routine_inspect")
@Where(clause = "is_deleted = 0")
public class RoutineInspect extends GenericEntity {
    @Transient
    private InspectTemplate inspectTemplate;
    @Transient
    private InspectStep inspectStep;



    @Column
    private Long referenceId;//引用的脚本/模板ID

    @Column
    private Integer type;


    public enum  Type{
        STEP,TEMPLATE
    }
}

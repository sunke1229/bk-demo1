package com.bocloud.blueking.model.db;


import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 常规巡检和定时巡检关联表
 */
@Entity
@Data
@Table(name = "t_routine_inspect_timing_inspect")
@Where(clause = "is_deleted = 0")
public class RoutineInspectTimingInspect extends GenericEntity{

    @Column
    private Long routineInspectId;

    @Column
    private Long timingInspectId;


}

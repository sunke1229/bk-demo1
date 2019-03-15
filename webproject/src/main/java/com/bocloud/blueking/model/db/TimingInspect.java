package com.bocloud.blueking.model.db;


import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * 定时巡检
 */
@Entity
@Data
@Table(name = "t_timing_inspect")
@Where(clause = "is_deleted = 0")
public class TimingInspect extends GenericEntity {
    @Column
    private String cron;

    /*@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "t_routine_inspect_timing_inspect", joinColumns = {
            @JoinColumn(name = "timing_inspect_id", referencedColumnName = "id",foreignKey = @ForeignKey(name="none"))},
            inverseJoinColumns = {@JoinColumn(name = "routine_inspect_id", referencedColumnName = "id",foreignKey = @ForeignKey(name="none"))})*/
    @Transient
    private List<RoutineInspect> routineInspects;
    @Transient
    private List<Long> routineInspectIds;


    //TODO 注意bizId
}

package com.bocloud.blueking.model.db;


import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "t_inspect_template")
@Where(clause = "is_deleted = 0")
public class InspectTemplate  extends GenericEntity {
    @Column
    private Integer type;

    @Column
    private Long referenceId;//if type = REFERENCE

    @Column
    private String stepIds;  //if type = CUSTOM
    /*@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="template_id",referencedColumnName = "id",foreignKey = @ForeignKey(name="none"))*/
    @Transient
    private List<InspectStep> inspectSteps;  //--->根据stepIds查询

    public enum Type {
        //自定义 ， 引用
        CUSTOM , REFERENCE
    }

    //TODO 开发时注意bizId
}

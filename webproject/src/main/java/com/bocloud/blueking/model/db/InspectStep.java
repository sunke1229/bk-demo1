package com.bocloud.blueking.model.db;

import com.tencent.bk.api.job.model.IP;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Table(name = "t_inspect_step")
@Where(clause = "is_deleted = 0")
public class InspectStep extends GenericEntity {
    @Column
    private Integer stepOrder;

    @Column
    private Long inspectTemplateId;

    @Column
    private Long scriptId;

    @Column
    private String ipList;

    @Column
    private String param;

    @Column
    private Integer timeout;

    @Column
    private String account;

}

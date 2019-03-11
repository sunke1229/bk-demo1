package com.bocloud.blueking.repository;

import com.bocloud.blueking.model.db.RoutineInspectTimingInspect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoutineInspectTimingInspectRepository extends CrudRepository<RoutineInspectTimingInspect,Long>, JpaRepository<RoutineInspectTimingInspect, Long>,JpaSpecificationExecutor<RoutineInspectTimingInspect> {
    List<RoutineInspectTimingInspect> findByTimingInspectId(Long  timingInspectId);
    @Modifying
    @Query(value = "update  RoutineInspectTimingInspect set isDeleted = true , modifierId = ?2  ,modifyTime = now() where timingInspectId = ?1 " )
    Integer deleteByTimingInspectId(Long modifierId,Long timingInspectId);
}

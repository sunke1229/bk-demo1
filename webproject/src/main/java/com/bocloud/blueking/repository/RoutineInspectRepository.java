package com.bocloud.blueking.repository;

import com.bocloud.blueking.model.db.RoutineInspect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface RoutineInspectRepository extends CrudRepository<RoutineInspect,Long>, JpaRepository<RoutineInspect, Long>,JpaSpecificationExecutor<RoutineInspect> {
    List<RoutineInspect> findByIdIn(List<Long> ids);
    @Modifying
    @Query(value = "update  RoutineInspect set isDeleted = true , modifierId = ?2 ,modifyTime = now() where id =?1" )
    Integer delete(Long id , Long modifierId);
}

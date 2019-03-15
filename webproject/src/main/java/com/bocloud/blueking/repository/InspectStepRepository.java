package com.bocloud.blueking.repository;

import com.bocloud.blueking.model.db.InspectStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InspectStepRepository extends CrudRepository<InspectStep,Long>, JpaRepository<InspectStep, Long>,JpaSpecificationExecutor<InspectStep> {

    List<InspectStep> findByIdIn(List<Long> ids);

    @Modifying
    @Query(value = "update  InspectStep set isDeleted = true , modifierId = ?2 ,modifyTime = now() where id in (?1)" )
    Integer deleteByIdsIn(String ids , Long userId);
}

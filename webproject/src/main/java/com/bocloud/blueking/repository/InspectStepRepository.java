package com.bocloud.blueking.repository;

import com.bocloud.blueking.model.db.InspectStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InspectStepRepository extends CrudRepository<InspectStep,Long>, JpaRepository<InspectStep, Long>,JpaSpecificationExecutor<InspectStep> {

    List<InspectStep> findByInspectTemplateId(Long id);
    @Modifying
    @Query(value = "update  InspectStep set isDeleted = true , modifierId = ?2 ,modifyTime = now() where inspectTemplateId =?1" )
    Integer deleteByInspectTemplateId(Long inspectTemplateId,Long userId);
}

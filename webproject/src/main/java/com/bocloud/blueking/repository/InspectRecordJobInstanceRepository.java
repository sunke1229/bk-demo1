package com.bocloud.blueking.repository;

import com.bocloud.blueking.model.db.InspectRecordJobInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface InspectRecordJobInstanceRepository extends CrudRepository<InspectRecordJobInstance,Long>, JpaRepository<InspectRecordJobInstance, Long>,JpaSpecificationExecutor<InspectRecordJobInstance> {
    List<InspectRecordJobInstance> findByInspectRecordId(Long id);

    @Modifying
    @Query(value = "update  InspectRecordJobInstance set isDeleted = true , modifierId = ?2 ,modifyTime = now() where inspectRecordId =?1" )
    Integer deleteByInspectRecordId(Long inspectRecordId , Long modifierId);
}

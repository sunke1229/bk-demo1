package com.bocloud.blueking.repository;

import com.bocloud.blueking.model.db.InspectRecord;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface InspectRecordRepository extends CrudRepository<InspectRecord,Long>, JpaRepository<InspectRecord, Long>,JpaSpecificationExecutor<InspectRecord> {
    @Modifying
    @Query( value=" update InspectRecord  set status =?2  ,modifierId=?3 ,modifyTime=now() where  id=?1")
    Integer updateStatus(Long id ,Integer status,Long userId);
    @Modifying
    @Query(value = "update  InspectRecord set isDeleted = true , modifierId = ?2 ,modifyTime = now() where id =?1" )
    Integer delete(Long id , Long modifierId);

    List<InspectRecord> findAllByStatus(Integer status);

}

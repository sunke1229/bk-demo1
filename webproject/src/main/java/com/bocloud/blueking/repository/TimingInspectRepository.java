/*
 *  Copyright (c) 2017.  Tencent 蓝鲸智云(BlueKing)
 */

package com.bocloud.blueking.repository;

import com.bocloud.blueking.model.db.TimingInspect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 定时巡检Dao
 */
@Repository //表示是一个DAO
public interface TimingInspectRepository extends CrudRepository<TimingInspect,Long>, JpaRepository<TimingInspect, Long>,JpaSpecificationExecutor<TimingInspect> {
    TimingInspect findTimingInspectById(Long id);
    @Modifying
    @Query(value = "update  TimingInspect set isDeleted = true , modifierId = ?2 ,modifyTime = now() where id =?1" )
    Integer delete(Long id , Long modifierId);
}

/*
 *  Copyright (c) 2017.  Tencent 蓝鲸智云(BlueKing)
 */

package com.bocloud.blueking.repository;

import com.bocloud.blueking.model.db.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository //表示是一个DAO
public interface UserRepository extends JpaRepository<User, Long>,JpaSpecificationExecutor<User> {

    User findUserByUsername(String userName);

    List<User> findUsersByChnameIsLike(String chname);

    @Query(value = "update  User set bizId = ?2  where id =?1")
     Integer changeBiz(Long userId ,Long bizId);
}

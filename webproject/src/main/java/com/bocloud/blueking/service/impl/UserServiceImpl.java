/*
 *  Copyright (c) 2017.  Tencent 蓝鲸智云(BlueKing)
 */

package com.bocloud.blueking.service.impl;

import com.bocloud.blueking.common.aop.annotation.ServiceAop;
import com.bocloud.blueking.dto.RespDto;
import com.bocloud.blueking.helper.RespHelper;
import com.bocloud.blueking.model.db.User;
import com.bocloud.blueking.repository.UserRepository;
import com.bocloud.blueking.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CURD Service示例
 */
@Service //用Spring的注解初始化以下Service类
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @ServiceAop
    public RespDto<User> save(User user) {
        User save = userRepository.save(user);
        if (save != null) {
            return RespHelper.ok(save);
        }
        return RespHelper.ok(null);
    }

    @Override
    @ServiceAop
    public RespDto<Long> delete(User user) {
        if (0 >= user.getId() && StringUtils.isBlank(user.getUsername())) {
            return RespHelper.fail(9999, "id和username同时不能为空！");
        }
        userRepository.delete(user);
        return RespHelper.ok(user.getId());
    }

    @Override
    public RespDto<Long> delete(Long id) {
        if (id <= 0 ) {
            return RespHelper.fail(9999, "id值不正确！");
        }
        userRepository.delete(id);
        return RespHelper.ok(id);
    }

    @Override
    @ServiceAop
    public RespDto<User> get(Long id) {
        User user = userRepository.findOne(id);
        if (user == null) {
            return RespHelper.ok(null);
        }
        return RespHelper.ok(user);
    }

    @Override
    @ServiceAop
    public User findByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            return null;
        }
        return user;
    }

    @Override
    public Long findIdByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user != null) {
            return user.getId();
        }
        return  null;
    }

    @Override
    @ServiceAop
    public RespDto<List<User>> findByChname(String chname) {
        List<User> users = userRepository.findUsersByChnameIsLike(chname);
        return RespHelper.ok(users);
    }

    @Override
    public RespDto<Long> changeBiz(Long userId, Long bizId) {
        if(userRepository.changeBiz(userId,bizId)>0){
            return  RespHelper.ok();
        }
        return  RespHelper.fail(9999,"修改业务失败");
    }

    @Override
    @ServiceAop
    public RespDto<Page<User>> findAll(Specification<User> spec, Pageable pageable) {
        Page<User> page = userRepository.findAll(spec, pageable);
        return RespHelper.ok(page);
    }
}

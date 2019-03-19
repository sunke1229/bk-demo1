package com.bocloud.blueking.service.impl;

import com.bocloud.blueking.common.exception.BusinessException;
import com.bocloud.blueking.common.util.IgnorePropertiesUtil;
import com.bocloud.blueking.dto.RespDto;
import com.bocloud.blueking.helper.RespHelper;
import com.bocloud.blueking.service.TimingInspectService;
import com.bocloud.blueking.model.db.RoutineInspect;
import com.bocloud.blueking.model.db.RoutineInspectTimingInspect;
import com.bocloud.blueking.model.db.TimingInspect;
import com.bocloud.blueking.repository.RoutineInspectRepository;
import com.bocloud.blueking.repository.RoutineInspectTimingInspectRepository;
import com.bocloud.blueking.repository.TimingInspectRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimingInspectServiceImpl implements TimingInspectService {

    @Autowired
    TimingInspectRepository timingInspectRepository;
    @Autowired
    RoutineInspectTimingInspectRepository routineInspectTimingInspectRepository;
    @Autowired
    RoutineInspectRepository routineInspectRepository;

    @Override
    public RespDto<TimingInspect> get(Long id) {
        TimingInspect timingInspect = timingInspectRepository.findOne(id);
        if(timingInspect!=null){
            List<RoutineInspectTimingInspect> rts = routineInspectTimingInspectRepository.findByTimingInspectId(timingInspect.getId());
            List<Long> routineIds = new ArrayList<>();
            for(RoutineInspectTimingInspect rt:rts){
                routineIds.add(rt.getRoutineInspectId());
            }
            List<RoutineInspect> routineInspects = routineInspectRepository.findByIdIn(routineIds);
            timingInspect.setRoutineInspects(routineInspects);
        }
        return  RespHelper.ok(timingInspect);
    }


    @Override
    public RespDto<Page<TimingInspect>> findAll(Pageable pageable) {
        return  RespHelper.ok(timingInspectRepository.findAll(pageable));
    }

    @Override
    public RespDto<Page<TimingInspect>> findAll(Specification<TimingInspect> spec, Pageable pageable) {
        return  RespHelper.ok(timingInspectRepository.findAll(spec,pageable));
    }

    @Transactional
    @Override
    public RespDto<TimingInspect> save(TimingInspect timingInspect,Long userId) {
        timingInspect.createNow(userId);
        return  RespHelper.ok(baseSave(timingInspect,userId));
    }
    @Transactional
    @Override
    public RespDto<Long> delete(Long id,Long userId) {
        TimingInspect beanInDb = timingInspectRepository.findOne(id);
        if(beanInDb==null){
            return  RespHelper.fail(9999,"要删除的定时巡检不存在");
        }
        routineInspectTimingInspectRepository.deleteByTimingInspectId(id,userId);
        timingInspectRepository.delete(id,userId);
        return  RespHelper.ok(id);
    }

    @Override
    public RespDto<TimingInspect> update(TimingInspect timingInspect,Long userId) {
        if(timingInspect!=null&&timingInspect.getId()!=null){
            routineInspectTimingInspectRepository.deleteByTimingInspectId(timingInspect.getId(),userId);
        }
        TimingInspect beanInDb = timingInspectRepository.findOne(timingInspect.getId());
        if(beanInDb==null){
            return  RespHelper.fail(9999,"未查询到要修改的定时巡检");
        }
        BeanUtils.copyProperties(timingInspect, beanInDb,IgnorePropertiesUtil.getNullPropertyNames(timingInspect));
        timingInspect.modifyNow(userId);
        return  RespHelper.ok(this.baseSave(beanInDb,userId));
    }



    private TimingInspect baseSave(TimingInspect timingInspect,Long userId){
        TimingInspect newTimingInspect = timingInspectRepository.save(timingInspect);
        List<Long> routineInspectIds = timingInspect.getRoutineInspectIds();
        for(Long routineInspectId:routineInspectIds){
            RoutineInspectTimingInspect rt = new RoutineInspectTimingInspect();
            //TODO 其他属性
            rt.setRoutineInspectId(routineInspectId);
            rt.setTimingInspectId(newTimingInspect.getId());
            rt.createNow(userId);
            routineInspectTimingInspectRepository.save(rt);
        }
        return newTimingInspect;
    }
}

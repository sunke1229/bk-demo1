package com.bocloud.blueking.service.impl;

import com.bocloud.blueking.common.util.IgnorePropertiesUtil;
import com.bocloud.blueking.dto.RespDto;
import com.bocloud.blueking.helper.RespHelper;
import com.bocloud.blueking.model.db.InspectStep;
import com.bocloud.blueking.repository.InspectStepRepository;
import com.bocloud.blueking.service.RoutineInspectService;
import com.bocloud.blueking.model.db.InspectTemplate;
import com.bocloud.blueking.model.db.RoutineInspect;
import com.bocloud.blueking.repository.InspectTemplateRepository;
import com.bocloud.blueking.repository.RoutineInspectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.BeanUtils;


@Service
public class RoutineInspectServiceImpl implements RoutineInspectService {

    @Autowired
    RoutineInspectRepository routineInspectRepository;
    @Autowired
    InspectTemplateRepository inspectTemplateRepository;
    @Autowired
    InspectStepRepository  inspectStepRepository;

    @Override
    public RespDto<RoutineInspect> get(Long id) {
        RoutineInspect routineInspect =  routineInspectRepository.findOne(id);
        if(routineInspect!=null&&routineInspect.getType()!=null&&routineInspect.getReferenceId()!=null){
            if(routineInspect.getType().equals(RoutineInspect.Type.STEP)){
                InspectStep step  = inspectStepRepository.findOne(routineInspect.getReferenceId());
                routineInspect.setInspectStep(step);
            }else if(routineInspect.getType().equals(RoutineInspect.Type.TEMPLATE)){
                InspectTemplate template  = inspectTemplateRepository.findOne(routineInspect.getReferenceId());
                routineInspect.setInspectTemplate(template);
            }else{
                //
            }
        }
        return  RespHelper.ok(routineInspect);
    }

    @Override
    public RespDto<Page<RoutineInspect>> findAll(Pageable pageable) {
        return  RespHelper.ok(routineInspectRepository.findAll(pageable));
    }

    @Override
    public RespDto<Page<RoutineInspect>> findAll(Specification<RoutineInspect> spec, Pageable pageable) {
        return  RespHelper.ok(routineInspectRepository.findAll(spec,pageable));
    }

    @Override
    public RespDto<RoutineInspect> save(RoutineInspect routineInspect,Long userId) {
        routineInspect.createNow(userId);
        return  RespHelper.ok(routineInspectRepository.save(routineInspect));
    }
    @Transactional
    @Override
    public RespDto<Long> delete(Long id,Long userId) {
        RoutineInspect beanInDb = routineInspectRepository.findOne(id);
        if(beanInDb==null){
            return  RespHelper.fail(9999,"要删除的常规巡检不存在");
        }
        routineInspectRepository.delete(id,userId);
        return  RespHelper.ok(id);
    }

    @Override
    public RespDto<RoutineInspect> update(RoutineInspect routineInspect,Long userId)  {
        RoutineInspect beanInDb = routineInspectRepository.findOne(routineInspect.getId());
        if(beanInDb==null){
            return  RespHelper.fail(9999,"未查询到要修改的常规巡检");
        }
        BeanUtils.copyProperties(beanInDb,routineInspect, IgnorePropertiesUtil.getNullPropertyNames(beanInDb));
        routineInspect.modifyNow(userId);
        return  RespHelper.ok(routineInspectRepository.save(routineInspect));
    }
}

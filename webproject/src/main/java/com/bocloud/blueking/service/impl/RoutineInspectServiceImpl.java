package com.bocloud.blueking.service.impl;

import com.bocloud.blueking.common.exception.BusinessException;
import com.bocloud.blueking.common.util.IdsUtil;
import com.bocloud.blueking.common.util.IgnorePropertiesUtil;
import com.bocloud.blueking.dto.RespDto;
import com.bocloud.blueking.helper.RespHelper;
import com.bocloud.blueking.model.db.InspectStep;
import com.bocloud.blueking.model.db.User;
import com.bocloud.blueking.repository.InspectStepRepository;
import com.bocloud.blueking.repository.UserRepository;
import com.bocloud.blueking.service.InspectTemplateService;
import com.bocloud.blueking.service.RoutineInspectService;
import com.bocloud.blueking.model.db.InspectTemplate;
import com.bocloud.blueking.model.db.RoutineInspect;
import com.bocloud.blueking.repository.InspectTemplateRepository;
import com.bocloud.blueking.repository.RoutineInspectRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.BeanUtils;

import java.util.List;


@Service
public class RoutineInspectServiceImpl implements RoutineInspectService {

    @Autowired
    RoutineInspectRepository routineInspectRepository;
    @Autowired
    InspectTemplateRepository inspectTemplateRepository;
    @Autowired
    InspectStepRepository  inspectStepRepository;
    @Autowired
    InspectTemplateService inspectTemplateService;
    @Autowired
    UserRepository userRepository;

    @Override
    public RespDto<RoutineInspect> get(Long id) {
        RoutineInspect routineInspect =  routineInspectRepository.findOne(id);
        if(routineInspect!=null&&routineInspect.getType()!=null){
            if(routineInspect.getType().equals(RoutineInspect.Type.STEP.ordinal())){
                InspectStep step  = inspectStepRepository.findOne(routineInspect.getReferenceId());
                routineInspect.setInspectStep(step);
            }else if(routineInspect.getType().equals(RoutineInspect.Type.TEMPLATE.ordinal())){
                InspectTemplate template  = inspectTemplateService.get(routineInspect.getReferenceId()).getData();
                routineInspect.setInspectTemplate(template);
            }else{
                //
            }
        }
        return  RespHelper.ok(routineInspect);
    }

    @Override
    public RespDto<Page<RoutineInspect>> findAll(Pageable pageable) {
        Page<RoutineInspect> page = routineInspectRepository.findAll(pageable);
        return  RespHelper.ok(loadName(page));
    }

    @Override
    public RespDto<Page<RoutineInspect>> findAll(Specification<RoutineInspect> spec, Pageable pageable) {
        Page<RoutineInspect> page = routineInspectRepository.findAll(spec,pageable);
        return  RespHelper.ok(loadName(page));
    }

    private Page<RoutineInspect> loadName(Page<RoutineInspect> page){
        List<RoutineInspect> list = page.getContent();
        for(RoutineInspect ri : list){
            User creator = userRepository.findOne(ri.getCreatorId());
            ri.setCreatorName(creator==null?"":creator.getUsername());
            User modifier = userRepository.findOne(ri.getModifierId());
            ri.setModifierName(modifier==null?"":modifier.getUsername());
        }
        return  page;
    }

    @Override
    public RespDto<RoutineInspect> save(RoutineInspect routineInspect,Long userId)  {
        routineInspect.createNow(userId);
        try{
            return RespHelper.ok(baseSave(routineInspect,userId));
        }catch (BusinessException e){
            return  RespHelper.fail(e.getCode(),e.getMessage());
        }
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
        RoutineInspect beanInDb = this.get(routineInspect.getId()).getData();
        if(beanInDb==null){
            return  RespHelper.fail(9999,"未查询到要修改的常规巡检");
        }
        BeanUtils.copyProperties(routineInspect,beanInDb,IgnorePropertiesUtil.getNullPropertyNames(routineInspect));
        routineInspect.modifyNow(userId);
        try{
            return RespHelper.ok(baseSave(beanInDb,userId));
        }catch (BusinessException e){
            return  RespHelper.fail(e.getCode(),e.getMessage());
        }
    }


    private RoutineInspect baseSave(RoutineInspect routineInspect,Long userId) throws BusinessException {
        if(routineInspect.getType().equals(RoutineInspect.Type.STEP.ordinal())){
            InspectStep step =  routineInspect.getInspectStep();
            step.createNow(userId);
            InspectStep newInspectStep = inspectStepRepository.save(step);
            routineInspect.setReferenceId(newInspectStep.getId());
           return  routineInspectRepository.save(routineInspect);
        }else if(routineInspect.getType().equals(RoutineInspect.Type.TEMPLATE.ordinal())){
            InspectTemplate template = routineInspect.getInspectTemplate();
            //TODO
            throw  new BusinessException("暂不支持模板类型",9999);
        }else {
            throw  new BusinessException("未知的类型",9999);
        }
    }
}

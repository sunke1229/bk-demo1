package com.bocloud.blueking.service.impl;

import com.bocloud.blueking.common.util.IdsUtil;
import com.bocloud.blueking.common.util.IgnorePropertiesUtil;
import com.bocloud.blueking.dto.RespDto;
import com.bocloud.blueking.helper.RespHelper;
import com.bocloud.blueking.service.InspectTemplateService;
import com.bocloud.blueking.model.db.InspectStep;
import com.bocloud.blueking.model.db.InspectTemplate;
import com.bocloud.blueking.repository.InspectStepRepository;
import com.bocloud.blueking.repository.InspectTemplateRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class InspectTemplateServiceImpl implements InspectTemplateService {

    @Autowired
    InspectTemplateRepository inspectTemplateRepository;
    @Autowired
    InspectStepRepository inspectStepRepository;

    @Override
    public  RespDto<InspectTemplate> get(Long id) {
        InspectTemplate inspectTemplate = inspectTemplateRepository.findOne(id);
        if(inspectTemplate!=null){
            //自定义模板查询
            if(inspectTemplate.getType().equals(InspectTemplate.Type.CUSTOM.ordinal())){
                Long templateId = inspectTemplate.getId();
                if(templateId!=null){
                    List<Long> idsList = IdsUtil.transformStringToLongList(inspectTemplate.getStepIds());
                    List<InspectStep> steps =  inspectStepRepository.findByIdIn(idsList);
                    inspectTemplate.setInspectSteps(steps);
                }else{
                    //TODO 报错或忽略
                }
            }else{
                //引用模板
                // TODO  去查询模板信息
            }
        }
        return  RespHelper.ok(inspectTemplate);
    }

    @Override
    public RespDto<Page<InspectTemplate>>  findAll(Pageable pageable) {
        Page<InspectTemplate> page = inspectTemplateRepository.findAll(pageable);
        return  RespHelper.ok(page);
    }

    @Override
    public RespDto<Page<InspectTemplate>> findAll(Specification<InspectTemplate> spec, Pageable pageable) {
        Page<InspectTemplate> page = inspectTemplateRepository.findAll(spec,pageable);
        return  RespHelper.ok(page);
    }

    @Transactional
    @Override
    public RespDto<InspectTemplate> save(InspectTemplate inspectTemplate,Long userId) {
        inspectTemplate.createNow(userId);
        return  RespHelper.ok(saveBase(inspectTemplate,userId));
    }

    @Transactional
    @Override
    public RespDto<Long> delete(Long id,Long userId) {
        InspectTemplate beanInDb = inspectTemplateRepository.findOne(id);
        if(beanInDb==null){
            return  RespHelper.fail(9999,"要删除的巡检模板不存在");
        }
        inspectStepRepository.deleteByIdsIn(beanInDb.getStepIds(),userId);
        inspectTemplateRepository.delete(id,userId);
        return  RespHelper.ok(id);
    }

    @Transactional
    @Override
    public RespDto<InspectTemplate> update(InspectTemplate inspectTemplate,Long userId) {
        if(inspectTemplate!=null&&inspectTemplate.getId()!=null){
            //删除所有步骤
            InspectTemplate beanInDb = inspectTemplateRepository.findOne(inspectTemplate.getId());
            if(beanInDb==null){
                return  RespHelper.fail(9999,"未查询到要修改的巡检模板");
            }
            BeanUtils.copyProperties(inspectTemplate, beanInDb,IgnorePropertiesUtil.getNullPropertyNames(inspectTemplate));
            inspectTemplate.modifyNow(userId);
            inspectStepRepository.deleteByIdsIn(inspectTemplate.getStepIds() ,userId);
            inspectTemplate.modifyNow(userId);
            return  RespHelper.ok(saveBase(beanInDb,userId));
        }else{
            //TODO ERROR
            return null;
        }
    }


    private InspectTemplate saveBase(InspectTemplate inspectTemplate,Long userId){
        List<InspectStep> steps = inspectTemplate.getInspectSteps();
        if(steps!=null){
            for(InspectStep inspectStep :steps){
                inspectStep.createNow(userId);
                inspectStepRepository.save(inspectStep);
            }
        }
        String idsString  = IdsUtil.getIdsStringFromBeanList(steps);
        inspectTemplate.setStepIds(idsString);
        return inspectTemplateRepository.save(inspectTemplate);
    }
}

package com.bocloud.blueking.service.impl;

import com.bocloud.blueking.common.util.IgnorePropertiesUtil;
import com.bocloud.blueking.dto.RespDto;
import com.bocloud.blueking.helper.RespHelper;
import com.bocloud.blueking.service.InspectRecordService;
import com.bocloud.blueking.model.db.InspectRecord;
import com.bocloud.blueking.model.db.InspectRecordJobInstance;
import com.bocloud.blueking.repository.InspectRecordItemRepository;
import com.bocloud.blueking.repository.InspectRecordRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InspectRecordServiceImpl implements InspectRecordService {
    @Autowired
    InspectRecordRepository inspectRecordRepository;
    @Autowired
    InspectRecordItemRepository inspectRecordItemRepository;

    @Override
    public RespDto<InspectRecord> get(Long id) {
        InspectRecord inspectRecord =  inspectRecordRepository.findOne(id);
        if(inspectRecord!=null){
            List<InspectRecordJobInstance> instances = inspectRecordItemRepository.findByInspectRecordId(inspectRecord.getId());
            if(instances!=null){
                inspectRecord.setInspectRecordJobInstances(instances);
            }
        }
        return RespHelper.ok(inspectRecord);
    }

    @Override
    public RespDto<Page<InspectRecord>> findAll(Pageable pageable) {
        Page page =  inspectRecordRepository.findAll(pageable);
        return  RespHelper.ok(page);
    }

    @Override
    public RespDto<Page<InspectRecord>> findAll(Specification<InspectRecord> spec, Pageable pageable) {
        Page<InspectRecord> page =  inspectRecordRepository.findAll(pageable);
        return RespHelper.ok(page);
    }

    @Override
    public RespDto<InspectRecord> save(InspectRecord inspectRecord, Long userId) {
        inspectRecord.createNow(userId);
        return  RespHelper.ok(baseSave(inspectRecord,userId));
    }

    @Override
    public RespDto<InspectRecord> update(InspectRecord inspectRecord, Long userId)  {
        List<InspectRecordJobInstance> inspectRecordItems = inspectRecordItemRepository.findByInspectRecordId(inspectRecord.getId());
        inspectRecordItemRepository.deleteByInspectRecordId(inspectRecord.getId(),userId);
        InspectRecord beanInDb = inspectRecordRepository.findOne(inspectRecord.getId());
        if(beanInDb==null){
            return  RespHelper.fail(9999,"未查询到要修改的巡检记录");
        }
        BeanUtils.copyProperties(beanInDb,inspectRecord, IgnorePropertiesUtil.getNullPropertyNames(beanInDb));
        inspectRecord.modifyNow(userId);
        return  RespHelper.ok(baseSave(inspectRecord,userId));
    }
    @Transactional
    @Override
    public RespDto<Long> delete(Long id,Long userId) {
        InspectRecord beanInDb = inspectRecordRepository.findOne(id);
        if(beanInDb==null){
            return  RespHelper.fail(9999,"要删除的巡检记录不存在");
        }
        List<InspectRecordJobInstance> inspectRecordItems = inspectRecordItemRepository.findByInspectRecordId(id);
        inspectRecordItemRepository.deleteByInspectRecordId(id,userId);
        inspectRecordRepository.delete(id,userId);
        return RespHelper.ok(id);
    }



    @Override
    @Transactional
    public RespDto updateStatus(Long inspectRecordId,Integer status,Long userId) {
        if(inspectRecordRepository.updateStatus(inspectRecordId,status,userId)>0){
            return  RespHelper.ok();
        }
        return  RespHelper.fail(9999,"修改失败");
    }


    private InspectRecord baseSave(InspectRecord inspectRecord, Long userId){
       InspectRecord newInspectRecord=  inspectRecordRepository.save(inspectRecord);
       List<InspectRecordJobInstance>  instances = inspectRecord.getInspectRecordJobInstances();
       for(InspectRecordJobInstance instance :instances){
           instance.createNow(userId);
           instance.setInspectRecordId(newInspectRecord.getId());
           inspectRecordItemRepository.save(instance);
       }
       return  newInspectRecord;
    }
}

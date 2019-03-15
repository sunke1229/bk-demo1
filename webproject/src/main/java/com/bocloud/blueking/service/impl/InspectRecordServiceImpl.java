package com.bocloud.blueking.service.impl;

import com.bocloud.blueking.common.util.IgnorePropertiesUtil;
import com.bocloud.blueking.dto.RespDto;
import com.bocloud.blueking.helper.RespHelper;
import com.bocloud.blueking.model.bussiness.JobData;
import com.bocloud.blueking.model.db.User;
import com.bocloud.blueking.service.InspectRecordService;
import com.bocloud.blueking.model.db.InspectRecord;
import com.bocloud.blueking.model.db.InspectRecordJobInstance;
import com.bocloud.blueking.repository.InspectRecordJobInstanceRepository;
import com.bocloud.blueking.repository.InspectRecordRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class InspectRecordServiceImpl implements InspectRecordService {
    @Autowired
    InspectRecordRepository inspectRecordRepository;
    @Autowired
    InspectRecordJobInstanceRepository inspectRecordJobInstanceRepository;

    @Override
    public RespDto<InspectRecord> get(Long id) {
        InspectRecord inspectRecord =  inspectRecordRepository.findOne(id);
        if(inspectRecord!=null){
            List<InspectRecordJobInstance> instances = inspectRecordJobInstanceRepository.findByInspectRecordId(inspectRecord.getId());
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
        Page<InspectRecord> page =  inspectRecordRepository.findAll(spec,pageable);
        return RespHelper.ok(page);
    }

    @Override
    public RespDto<InspectRecord> save(InspectRecord inspectRecord, Long userId) {
        inspectRecord.createNow(userId);
        return  RespHelper.ok(baseSave(inspectRecord,userId));
    }

    @Override
    public RespDto<InspectRecord> update(InspectRecord inspectRecord, Long userId)  {
        List<InspectRecordJobInstance> inspectRecordJobInstances = inspectRecordJobInstanceRepository.findByInspectRecordId(inspectRecord.getId());
        inspectRecordJobInstanceRepository.deleteByInspectRecordId(inspectRecord.getId(),userId);
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
        List<InspectRecordJobInstance> inspectRecordJobInstances = inspectRecordJobInstanceRepository.findByInspectRecordId(id);
        inspectRecordJobInstanceRepository.deleteByInspectRecordId(id,userId);
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

    @Override
    public List<InspectRecord> findAllRunning() {
        return  inspectRecordRepository.findAllByStatus(0);
    }

    @Override
    @Transactional
    public void synInstance(Long instanceId, Integer status) {
        Integer recordStatus;
        if(status==3){
            recordStatus = 1;
        }else if(status<3){
            recordStatus = 0;
        }else{
            recordStatus = 2;
        }

        InspectRecordJobInstance instance =  inspectRecordJobInstanceRepository.findByReferenceInstanceId(instanceId);
        InspectRecord record = inspectRecordRepository.findOne(instance.getInspectRecordId());
        Integer recordType = record.getType();
        //FAST_SCRIPT,FAST_TEMPLATE,ROUTINE ,TIMING
        if(!recordType.equals(JobData.Type.TIMING.ordinal())){
            inspectRecordRepository.updateStatus(record.getId(),recordStatus, User.SYSTEM_ID);
        }else{
            //TODO  定时巡检
            //一个或者多个常规
        }

    }

    @Override
    public Long count(Specification<InspectRecord> spec) {
        return inspectRecordRepository.count(spec);
    }


    private InspectRecord baseSave(InspectRecord inspectRecord, Long userId){
       InspectRecord newInspectRecord=  inspectRecordRepository.save(inspectRecord);
       List<InspectRecordJobInstance>  instances = inspectRecord.getInspectRecordJobInstances();
       for(InspectRecordJobInstance instance :instances){
           instance.createNow(userId);
           instance.setInspectRecordId(newInspectRecord.getId());
           inspectRecordJobInstanceRepository.save(instance);
       }
       return  newInspectRecord;
    }
}

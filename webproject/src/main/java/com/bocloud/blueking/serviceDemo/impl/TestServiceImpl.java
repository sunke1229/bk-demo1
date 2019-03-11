package com.bocloud.blueking.serviceDemo.impl;

import com.bocloud.blueking.service.InspectRecordService;
import com.bocloud.blueking.service.InspectTemplateService;
import com.bocloud.blueking.model.db.InspectStep;
import com.bocloud.blueking.model.db.InspectTemplate;
import com.bocloud.blueking.serviceDemo.TestService;
import com.tencent.bk.utils.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    InspectTemplateService inspectTemplateDao;
    @Autowired
    InspectRecordService inspectRecordService;
    @Override
    public String test() {
        return testDelete();
    }

    private String testSave(){
        InspectTemplate template = new InspectTemplate();
        template.setName("template1");
        template.setType(InspectTemplate.Type.CUSTOM.ordinal());
        List<InspectStep> steps = new ArrayList<>();
        for(int i = 0 ; i < 2 ;i++){
            InspectStep step = new InspectStep();
            step.setStepOrder(0);
            step.setName("step"+i);
            steps.add(step);
        }
        template.setInspectSteps(steps);
        inspectTemplateDao.save(template,(long)1);
        return JsonUtil.toJson(template);
    }

    private String testDelete(){
        return  inspectTemplateDao.delete((long)1,(long)1).toString();
    }

    private String testFindAll(){
        Pageable pageable= new PageRequest(0,10);
        return  JsonUtil.toJson(inspectTemplateDao.findAll(pageable));
    }

    private String testUpdate() throws Exception {
        InspectTemplate template = new InspectTemplate();
        template.setId((long)1);
        template.setName("template2");
        template.setType(InspectTemplate.Type.CUSTOM.ordinal());
        List<InspectStep> steps = new ArrayList<>();
        for(int i = 0 ; i < 2 ;i++){
            InspectStep step = new InspectStep();
            step.setStepOrder(0);
            step.setName("step"+i);
            steps.add(step);
        }
        template.setInspectSteps(steps);
        return JsonUtil.toJson(inspectTemplateDao.update(template,(long)1));
    }

    private String testUpdateStatus(){
        return JsonUtil.toJson(inspectRecordService.updateStatus((long)1,1,(long)1));
    }

}

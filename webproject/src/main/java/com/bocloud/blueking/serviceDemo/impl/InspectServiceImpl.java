package com.bocloud.blueking.serviceDemo.impl;

import com.bocloud.blueking.model.db.TimingInspect;
import com.bocloud.blueking.repository.TimingInspectRepository;
import com.bocloud.blueking.serviceDemo.InspectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InspectServiceImpl implements InspectService {
    @Autowired
    TimingInspectRepository timingInspectRepository;
    public TimingInspect findTimingInspectById(Long id){
        return timingInspectRepository.findTimingInspectById(id);
    }
}

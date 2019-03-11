package com.bocloud.blueking.serviceDemo;

import com.bocloud.blueking.model.db.TimingInspect;

public interface InspectService {
    TimingInspect findTimingInspectById(Long id);
}

/*
 *  Copyright (c) 2017.  Tencent 蓝鲸智云(BlueKing)
 */

package com.bocloud.blueking.init.bean;

import com.tencent.bk.api.cc.CCApi;
import com.tencent.bk.api.job.JobApi;
import com.tencent.bk.api.paas.BKPaaSApi;
import com.tencent.bk.core.init.BkCoreProperties;
import com.tencent.bk.core.sdk.cmdb.CMDBClient;
import com.tencent.bk.core.sdk.paas.PAASClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
public class ServiceBeanConfiguration {


    @Bean
    public CMDBClient getcmdbClient(@Autowired BkCoreProperties bkCoreProperties) {
        return new CMDBClient(bkCoreProperties);
    }

    @Bean
    public PAASClient getPaasClient(@Autowired BkCoreProperties bkCoreProperties) {
        return new PAASClient(bkCoreProperties);
    }

    @Bean
    public BKPaaSApi getBKPaaSApi(@Autowired BkCoreProperties bkCoreProperties) {
        return new BKPaaSApi(bkCoreProperties);
    }

    @Bean
    public JobApi getJobApi(@Autowired BkCoreProperties bkCoreProperties) {
        return new JobApi(bkCoreProperties);
    }


    @Bean
    public CCApi getCCApi(@Autowired BkCoreProperties bkCoreProperties) {
        return new CCApi(bkCoreProperties);
    }

}

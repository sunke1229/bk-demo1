package com.tencent.bk.api.job.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 作业主结构
 */
@Data
public class ScriptList {
    @JsonProperty("data")
    private List<Script> data;

    @JsonProperty("start")
    private long start;

    @JsonProperty("page_size")
    private long pageSize;

    @JsonProperty("total_record_size")
    private long totalRecordSize;

}
package com.ht.commonactivity.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SetCandidateGroupVo {
    @ApiParam("流程实例id")
    private String proInstId;
    private Map<String,List<String>> paramMap;

    @ApiParam("任务id")
    String taskId;
    @ApiParam("自定义候选人列表")
    private List<String> candidateUser;
}

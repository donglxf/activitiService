package com.ht.commonactivity.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SetCandidateGroupVo {
    private String proInstId;
    private Map<String,List<String>> paramMap;

    String taskId;
    private List<String> candidateUser;
}

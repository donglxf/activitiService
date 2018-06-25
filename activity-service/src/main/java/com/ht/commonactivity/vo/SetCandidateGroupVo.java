package com.ht.commonactivity.vo;

import lombok.Data;

import java.util.List;

@Data
public class SetCandidateGroupVo {
    String taskId;
    private List<String> candidateUser;
}

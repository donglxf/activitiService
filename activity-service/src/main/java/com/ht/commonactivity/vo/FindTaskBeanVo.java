package com.ht.commonactivity.vo;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;
import java.util.Map;


public class FindTaskBeanVo {
    /**
     * 用户名
     */
    @ApiParam("用户名，findTaskByAssignee接口比传")
    private String assignee;

    private List<Map<String, Object>> paramMap;

    private int firstResult = 0;

    private int maxResults = 10;

    private String taskDefinId;

    public String getTaskDefinId() {
        return taskDefinId;
    }

    public void setTaskDefinId(String taskDefinId) {
        this.taskDefinId = taskDefinId;
    }

    public List<Map<String, Object>> getParamMap() {
        return paramMap;
    }

    public void setParamMap(List<Map<String, Object>> paramMap) {
        this.paramMap = paramMap;
    }

    public String getCandidateUser() {
        return candidateUser;
    }

    public void setCandidateUser(String candidateUser) {
        this.candidateUser = candidateUser;
    }

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    /**
     * 候选人
     */

    private String candidateUser;

    /**
     * 候选组
     */
    @ApiParam("候选组名，findTaskByCandidateGroup接口必传")
    private List<String> candidateGroup;

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public List<String> getCandidateGroup() {
        return candidateGroup;
    }

    public void setCandidateGroup(List<String> candidateGroup) {
        this.candidateGroup = candidateGroup;
    }
}

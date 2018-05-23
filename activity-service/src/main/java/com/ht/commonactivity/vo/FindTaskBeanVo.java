package com.ht.commonactivity.vo;

import java.util.List;
import java.util.Map;


public class FindTaskBeanVo {
    /**
     * 用户名
     */
    private String assignee;

    private Map<String, Object> paramMap;  // json格式

    private int firstResult = 0;

    private int maxResults = 10;

    private String taskDefinId;

    public String getTaskDefinId() {
        return taskDefinId;
    }

    public void setTaskDefinId(String taskDefinId) {
        this.taskDefinId = taskDefinId;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
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

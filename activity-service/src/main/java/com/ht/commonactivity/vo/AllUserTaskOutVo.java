package com.ht.commonactivity.vo;

import lombok.Data;

import java.util.List;

public class AllUserTaskOutVo {
    private String taskName;
    private String taskDefinedId;
    private String assignName;
    private List<String> canditionUserGroup;

    public List<String> getCanditionUserGroup() {
        return canditionUserGroup;
    }

    public void setCanditionUserGroup(List<String> canditionUserGroup) {
        this.canditionUserGroup = canditionUserGroup;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDefinedId() {
        return taskDefinedId;
    }

    public void setTaskDefinedId(String taskDefinedId) {
        this.taskDefinedId = taskDefinedId;
    }

    public String getAssignName() {
        return assignName;
    }

    public void setAssignName(String assignName) {
        this.assignName = assignName;
    }
}

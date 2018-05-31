package com.ht.commonactivity.vo;

import java.util.List;

public class TaskGroup {
    private String taskId;
    private String taskName;
    private List<String> roleGroup;
    private String userName;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public List<String> getRoleGroup() {
        return roleGroup;
    }

    public void setRoleGroup(List<String> roleGroup) {
        this.roleGroup = roleGroup;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

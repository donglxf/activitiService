package com.ht.commonactivity.vo;

import java.util.List;

public class TaskRoleAssignResult {
    private List<TaskGroup> role;
    private List<TaskGroup> user;

    public List<TaskGroup> getRole() {
        return role;
    }

    public void setRole(List<TaskGroup> role) {
        this.role = role;
    }

    public List<TaskGroup> getUser() {
        return user;
    }

    public void setUser(List<TaskGroup> user) {
        this.user = user;
    }
}

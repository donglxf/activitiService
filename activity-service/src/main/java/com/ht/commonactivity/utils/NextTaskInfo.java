package com.ht.commonactivity.utils;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class NextTaskInfo {
    private String procInstId; // 实例id
    private String taskDefineKey; // 定义key, 格式：sid-26585B1A-9680-4331-AD31-7A107BA03AB7
    private String taskText; // 节点名
    private String taskAssign; // 任务办理人
    private String proIsEnd; // 流程是否结束  Y-结束
    private String procDefinedId;
    private String taskId; // 任务id

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProcDefinedId() {
        return procDefinedId;
    }

    public void setProcDefinedId(String procDefinedId) {
        this.procDefinedId = procDefinedId;
    }

    public String getProIsEnd() {
        return proIsEnd;
    }

    public void setProIsEnd(String proIsEnd) {
        this.proIsEnd = proIsEnd;
    }

    public String getTaskAssign() {
        return taskAssign;
    }

    public void setTaskAssign(String taskAssign) {
        this.taskAssign = taskAssign;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getTaskDefineKey() {
        return taskDefineKey;
    }

    public void setTaskDefineKey(String taskDefineKey) {
        this.taskDefineKey = taskDefineKey;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }
}

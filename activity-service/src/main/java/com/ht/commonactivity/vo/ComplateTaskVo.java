package com.ht.commonactivity.vo;

import io.swagger.annotations.ApiParam;

import java.util.Map;

public class ComplateTaskVo {
    @ApiParam("任务id")
    private String taskId; // 任务id
    @ApiParam("办理意见")
    private String opinion; // 办理意见
    @ApiParam("办理者")
    private String userName; //办理者
//    private String proInstId; // 流程实例id
//    private Map<String, Object> data; // 业务数据

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }
}

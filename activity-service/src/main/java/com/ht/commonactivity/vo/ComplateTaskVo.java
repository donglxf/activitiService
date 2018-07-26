package com.ht.commonactivity.vo;

import com.ht.commonactivity.common.BaseProcessParamter;
import io.swagger.annotations.ApiParam;

import java.util.List;
import java.util.Map;

public class ComplateTaskVo extends BaseProcessParamter {
    @ApiParam("任务id")
    private String taskId; // 任务id
    @ApiParam("办理意见")
    private String opinion; // 办理意见
    @ApiParam("办理者")
    private String userName; //办理者
    @ApiParam("审批参数")
    private Map<String, Object> data;

    @ApiParam("候选人组")
    private List<String> candidateUser; // 对应变量 ${hxUser}


    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    private String param;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

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

    public List<String> getCandidateUser() {
        return candidateUser;
    }

    public void setCandidateUser(List<String> candidateUser) {
        this.candidateUser = candidateUser;
    }
}

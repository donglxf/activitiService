package com.ht.commonactivity.common;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class RpcStartParamter implements Serializable {

    /**
     * 业务key ,启动流程是传入
     */
    private String businessKey;
    /**
     * 模型定义ID
     */
    private String procDefId;

    /**
     * 流程定义key  ,启动流程是传入
     */
    private String processDefinedKey;

    private String userId;
    /**
     * 流程版本
     */
    private String version;
    /**
     * 批次号
     */
    private Long batchId;
    /**
     * 类型，1 验证，2 正式调用
     */
    private String type;

//    private Long taskId;

    private Map<String,Object> data; // 业务数据
    /**
     * 批次大小
     */
    private int batchSize;
    /**
     * 批量调用流程参数
     */
//    private List<Map<String,Object>> datas;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBusinessKey() {
            return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getProcessDefinedKey() {
        return processDefinedKey;
    }

    public void setProcessDefinedKey(String processDefinedKey) {
        this.processDefinedKey = processDefinedKey;
    }
}

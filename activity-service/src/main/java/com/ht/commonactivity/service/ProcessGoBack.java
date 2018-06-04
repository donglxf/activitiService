package com.ht.commonactivity.service;

import com.ht.commonactivity.vo.ProjectWorkflowRequest;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.pvm.process.ActivityImpl;

import java.util.List;
import java.util.Map;

public interface ProcessGoBack  {

    /**
     *
     * @param taskId 任务id
     * @param msg 批注
     * @param endActivityId 结束节点的activitiyId
     * @return 当前节点定义id：sid-BE4054B0-8AC1-4A9F-985E-D9E4FABD2D17
     * @throws Exception
     */
    public String turnBackNew(String taskId, String msg, String endActivityId,String toBackNoteId ) throws Exception;

    /**
     * 根据任务ID和节点ID获取活动节点 <br>
     * @param taskId
     * @param activityId
     * @return
     * @throws Exception
     */
    public ActivityImpl findActivitiImpl(String taskId, String activityId)
            throws Exception;

    /**
     *
     * @param taskId
     * @param variables
     * @param activityId
     * @throws Exception
     */
    public void commitProcess(String taskId, Map<String, Object> variables,
                              String activityId) throws Exception;
    /**
     * 会签退回
     * @param param
     * @param user
     * @throws Exception
     */
    public void submitTask(ProjectWorkflowRequest param, User user) throws Exception ;

    /**
     *
     * @param taskId
     * @return
     * @throws Exception
     */
    public List<ActivityImpl> getactivities(String taskId)  throws Exception ;
//    public List<ActivityImpl> getactivities(HistoricTaskInstance currTask, ProcessInstance instance, String taskId)  throws Exception

}

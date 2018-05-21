package com.ht.commonactivity.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ht.commonactivity.common.ActivitiConstants;
import com.ht.commonactivity.common.RpcStartParamter;
import com.ht.commonactivity.common.enumtype.ActivitiSignEnum;
import com.ht.commonactivity.common.result.Result;
import com.ht.commonactivity.entity.ActProcRelease;
import com.ht.commonactivity.rpc.UcAppRpc;
import com.ht.commonactivity.service.*;
import com.ht.commonactivity.vo.ComplateTaskVo;
import com.ht.commonactivity.vo.FindTaskBeanVo;
import com.ht.commonactivity.vo.TaskVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.*;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api("工作流对外提供的接口服务")
@Log4j2
public class ActivitiOutServiceController implements ModelDataJsonConstants {

    @Resource
    protected RepositoryService repositoryService;

    @Autowired
    protected HistoryService historyService;

    @Resource
    protected ObjectMapper objectMapper;
    @Resource
    protected ActivitiService activitiService;

    @Resource
    protected ActProcReleaseService actProcReleaseService;

    @Autowired
    protected TaskService taskService;

    @Autowired
    protected ActModelDefinitionService modelDefinitionService;

    @Autowired
    protected ProcessGoBack processGoBack;

    @Autowired
    protected ActProcessAuditHisService auditHisService;

    @Autowired
    protected ActProcessJumpHisService jumpHisService;

    @Autowired
    protected RuntimeService runtimeService;


    @Autowired
    protected UcAppRpc ucAppRpc;

    protected static volatile ProcessEngine processEngine = null;

    protected static ProcessEngine getProcessEngine() {
        synchronized (ProcessEngine.class) {
            if (processEngine == null) {
                processEngine = ProcessEngines.getDefaultProcessEngine();
            }
        }
        return processEngine;
    }

    @RequestMapping("/startProcessInstanceByKey")
    @ApiOperation("启动模型")
    public Result<String> startProcessInstanceByKey(@RequestBody RpcStartParamter paramter) {
        log.info("start model,paramter:" + JSON.toJSONString(paramter));
        Result<String> data = null;
        try {
            if (StringUtils.isEmpty(paramter.getBusinessKey())) {
                return Result.error(1, "businessKey is null ,check");
            }
            ActProcRelease release = null;
            // 版本信息为空，获取模型最新版本
            if (StringUtils.isEmpty(paramter.getVersion())) {
                release = activitiService.getModelLastedVersion(paramter.getProcessDefinedKey());
            } else {
                release = activitiService.getModelInfo(paramter.getProcessDefinedKey(), paramter.getVersion());
            }
            if (release == null) {
                return Result.error(1, ActivitiConstants.MODEL_UNEXIST);
            }

            List<ProcessInstance> processInstancs = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(paramter.getBusinessKey()).list();
            String deleteReason = "删除重新启动";
            if (processInstancs != null) {
                for (ProcessInstance processInstance : processInstancs) {
                    runtimeService.deleteProcessInstance(processInstance.getProcessInstanceId(), deleteReason);
                }
            }

            ProcessInstance instance = runtimeService.startProcessInstanceById(release.getModelProcdefId(),
                    paramter.getBusinessKey(), paramter.getData());

//            String processInstanceId = activitiService.startProcess(paramter);
            data = Result.success(instance.getId());
        } catch (Exception e) {
            data = Result.error(1, "模型启动异常！");
            log.error("deploy model error,error message：", e);
        }
        log.info("start model sucess.");
        return data;
    }

    /**
     * 任务转办
     *
     * @param taskId 任务id
     * @param owner  转办人
     * @return
     */
    @PostMapping("/taskChangeOther_abc")
    public Result<String> taksChangeOther(@RequestParam String taskId, @RequestParam String owner) {
        taskService.setAssignee(taskId, owner);
        return Result.success();
    }


    /**
     * 根据用户、候选人、候选组 查询所有任务
     */
    @RequestMapping("/findTaskByAssignee")
    @ResponseBody
    public Result<List<TaskVo>> findMyPersonalTask(@RequestBody FindTaskBeanVo vo) {
        List<TaskVo> voList = new ArrayList<>();
        Result<List<TaskVo>> data = null; // new ArrayList<TaskVo>();
//        泛型过滤重复对象
//        List<ActRuTask> tlist= activitiService.findTaskByAssigneeOrGroup(vo);
//        List<ActRuTask> list1= new ArrayList<ActRuTask>();
//        tlist.stream().forEach(p -> {
//            if(!list1.contains(p)){
//                list1.add(p);
//            }
//        });

        if (StringUtils.isEmpty(vo.getAssignee())) {
            data = Result.error(1, "参数异常！");
            return data;
        }


        TaskQuery query = getProcessEngine().getTaskService()//与正在执行的任务管理相关的Service
                .createTaskQuery();//创建任务查询对象

        if (com.ht.commonactivity.utils.ObjectUtils.isNotEmpty(vo.getParamMap())) {
            Map<String, Object> o = vo.getParamMap();
            if (ActivitiSignEnum.equle.getVal().equals(o.get("type"))) {
                query.processVariableValueEquals(String.valueOf(o.get("name")), String.valueOf(o.get("value")));
            } else if (ActivitiSignEnum.notequle.getVal().equals(o.get("key"))) {
                query.processVariableValueNotEquals(String.valueOf(o.get("name")), String.valueOf(o.get("value")));
            } else if (ActivitiSignEnum.great.getVal().equals(o.get("key"))) {
                query.processVariableValueGreaterThan(String.valueOf(o.get("name")), String.valueOf(o.get("value")));
            } else if (ActivitiSignEnum.greatEq.getVal().equals(o.get("key"))) {
                query.processVariableValueGreaterThanOrEqual(String.valueOf(o.get("name")), String.valueOf(o.get("value")));
            } else if (ActivitiSignEnum.less.getVal().equals(o.get("key"))) {
                query.processVariableValueLessThan(String.valueOf(o.get("name")), String.valueOf(o.get("value")));
            } else if (ActivitiSignEnum.lessEq.getVal().equals(o.get("key"))) {
                query.processVariableValueLessThanOrEqual(String.valueOf(o.get("name")), String.valueOf(o.get("value")));
            } else if (ActivitiSignEnum.like.getVal().equals(o.get("key"))) {
                query.processVariableValueLike(String.valueOf(o.get("name")), String.valueOf(o.get("value")));
            }
        }

        /**查询条件（where部分）*/
        if (vo.getAssignee() != null) {
            query.taskAssignee(vo.getAssignee()); //指定个人任务查询，指定办理人
        }
        /**排序*/
        List<Task> list = query.orderByTaskCreateTime().asc().list();//返回列表
        if (list != null && list.size() > 0) {
            for (Task task : list) {
                TaskVo tvo = new TaskVo();
                tvo.setCreateTime(task.getCreateTime());
                tvo.setId(task.getId());
                tvo.setExecutionId(task.getExecutionId());
                tvo.setName(task.getName());
                tvo.setProcDefId(task.getProcessDefinitionId());
                tvo.setProInstId(task.getProcessInstanceId());
                tvo.setAssign(task.getAssignee());
                voList.add(tvo);
            }
        }
        return data = Result.success(voList);
    }

    /**
     * 完成任务
     */
    @PostMapping("/complateTask")
    @ResponseBody
    public Result<Integer> completeMyPersonalTask(@RequestBody ComplateTaskVo vo) {
        String taskId = vo.getTaskId();
        try {
            if (StringUtils.isEmpty(taskId)) {
                return Result.error(1, "参数不合法，taskId不能为空");
            }
            Task t = getProcessEngine().getTaskService().createTaskQuery().taskId(taskId).singleResult();
//        TaskInfo tt=  getProcessEngine().getHistoryService().createHistoricTaskInstanceQuery().taskId(taskId).singleResult();

            //完成任务的同时，设置流程变量，让流程变量判断连线该如何执行
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("flag", "2");
            TaskService service = getProcessEngine().getTaskService();
            Authentication.setAuthenticatedUserId(vo.getUserName()); // 添加批注设置审核人
            service.addComment(taskId, t.getProcessInstanceId(), vo.getOpinion());
            service.complete(taskId, variables);

            return Result.success(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error(1, "完成任务失败" + taskId);
    }


}

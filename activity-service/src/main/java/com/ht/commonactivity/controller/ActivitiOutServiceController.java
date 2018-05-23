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
import com.ht.commonactivity.utils.NextTaskInfo;
import com.ht.commonactivity.utils.ObjectUtils;
import com.ht.commonactivity.vo.ComplateTaskVo;
import com.ht.commonactivity.vo.FindTaskBeanVo;
import com.ht.commonactivity.vo.TaskVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.*;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.runtime.Execution;
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


    @Autowired
    protected HistoryService historyService;

    @Resource
    protected ActivitiService activitiService;

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
    public Result<List<NextTaskInfo>> startProcessInstanceByKey(@RequestBody RpcStartParamter paramter) {
        log.info("start model,paramter:" + JSON.toJSONString(paramter));
        Result<List<NextTaskInfo>> data = null;
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

            boolean bool = procIsEnd(instance.getId());
            List<NextTaskInfo> list = new ArrayList<NextTaskInfo>();
            List<Task> tasks = taskService.createTaskQuery().processInstanceId(instance.getId()).list();
            for (Task t : tasks) {
                NextTaskInfo result = new NextTaskInfo();
                result.setTaskDefineKey(t.getTaskDefinitionKey());
                result.setTaskText(t.getName());
                result.setProcInstId(instance.getId());
                result.setTaskAssign(t.getAssignee());
                result.setProIsEnd(bool ? "Y" : "N");
//                result.setTaskId(t.getId());
                list.add(result);
            }

//            String processInstanceId = activitiService.startProcess(paramter);
//            TaskDefinition taskDefinition = activitiService.getNextTaskInfoByProcessId(instance.getId());
//            NextTaskInfo result = new NextTaskInfo();
//            result.setTaskDefineKey(taskDefinition.getKey());
//            result.setTaskText(taskDefinition.getNameExpression().getExpressionText());
//            result.setProcInstId(instance.getId());
//            result.setTaskAssign(taskDefinition.getAssigneeExpression().getExpressionText());
            data = Result.success(list);
        } catch (Exception e) {
            data = Result.error(1, "模型启动异常！");
            log.error("deploy model error,error message：", e);
        }
        log.info("start model sucess.");
        return data;
    }

    /**
     * 判断流程是否结束
     *
     * @param proInstId
     * @return true-结束，false-未结束
     */
    public boolean procIsEnd(String proInstId) {
        ProcessInstance rpi = getProcessEngine().getRuntimeService()//
                .createProcessInstanceQuery()//创建流程实例查询对象
                .processInstanceId(proInstId)
                .singleResult();
        return rpi == null ? true : false;
    }

    /**
     * 任务转办,a办理人转给b办理，b办理完成之后流程往下走
     *
     * @param taskId 任务id
     * @param owner  转办人
     * @return
     */
    @PostMapping("/taskChangeOther")
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
            ProcessInstance pi = null;
            for (Task task : list) {
                if (pi == null) {
                    pi = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
                }
                TaskVo tvo = new TaskVo();
                tvo.setBusinessKey(pi.getBusinessKey());
                tvo.setCreateTime(task.getCreateTime());
                tvo.setTaskId(task.getId());
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
     * 根据候选组 查询所有代办任务,角色
     */
    @RequestMapping("/findTaskByCandidateGroup")
    @ResponseBody
    public Result<List<TaskVo>> findTaskByCandidateGroup(@RequestBody FindTaskBeanVo vo) {
        List<TaskVo> voList = new ArrayList<>();
        Result<List<TaskVo>> data = null;
        if (vo.getCandidateGroup() == null) {
            data = Result.error(1, "参数异常！");
            return data;
        }
        TaskQuery query = getProcessEngine().getTaskService()//与正在执行的任务管理相关的Service
                .createTaskQuery();
        if (ObjectUtils.isNotEmpty(vo.getTaskDefinId())) {
            query.taskDefinitionKeyLike(vo.getTaskDefinId());
//            if (vo.getTaskDefinId().size() == 1) {
//                query.processDefinitionKeyLike(vo.getTaskDefinId().get(0));
//            } else {
//                query.processDefinitionKeyIn(vo.getTaskDefinId());
//            }

        }
        List<Task> list = query.taskCandidateGroupIn(vo.getCandidateGroup())
                .orderByTaskCreateTime().desc().listPage(vo.getFirstResult(), vo.getMaxResults());
        ProcessInstance pi = null;
        for (Task task : list) {
            if (pi == null) {
                pi = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
            }
            TaskVo tvo = new TaskVo();
            tvo.setBusinessKey(pi.getBusinessKey());
            tvo.setCreateTime(task.getCreateTime());
            tvo.setTaskId(task.getId());
            tvo.setExecutionId(task.getExecutionId());
            tvo.setName(task.getName());
            tvo.setProcDefId(task.getProcessDefinitionId());
            tvo.setProInstId(task.getProcessInstanceId());
            tvo.setAssign(task.getAssignee());
            voList.add(tvo);
        }
        return data = Result.success(voList);
    }

    /**
     * 完成任务
     */
    @PostMapping("/complateTask")
    @ResponseBody
    public Result<List<NextTaskInfo>> completeMyPersonalTask(@RequestBody ComplateTaskVo vo) {
        List<NextTaskInfo> list = new ArrayList<NextTaskInfo>();
        String taskId = vo.getTaskId();
        try {
            if (StringUtils.isEmpty(taskId)) {
                return Result.error(1, "参数不合法，taskId不能为空");
            }
            Task t = getProcessEngine().getTaskService().createTaskQuery().taskId(taskId).singleResult();
//        TaskInfo tt=  getProcessEngine().getHistoryService().createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
//            TaskDefinition taskDefinition = activitiService.getNextTaskInfo(taskId, vo.getData());
//            NextTaskInfo result = new NextTaskInfo();
//            result.setTaskDefineKey(taskDefinition.getKey());
//            result.setTaskText(taskDefinition.getNameExpression().getExpressionText());
//            result.setProcInstId(vo.getProInstId());
//            result.setTaskAssign(taskDefinition.getAssigneeExpression().getExpressionText());
//            result.setProIsEnd(procIsEnd(vo.getProInstId()) ? "Y" : "N");
//            list.add(result);
            Task task = taskService.createTaskQuery().taskId(vo.getTaskId()).singleResult();

            //完成任务的同时，设置流程变量，让流程变量判断连线该如何执行
            TaskService service = getProcessEngine().getTaskService();
            Authentication.setAuthenticatedUserId(vo.getUserName()); // 添加批注设置审核人,记入日志
            service.addComment(taskId, t.getProcessInstanceId(), vo.getOpinion());
            service.complete(taskId);
            List<Task> taskList = taskService.createTaskQuery().processDefinitionId(task.getProcessDefinitionId()).list();
            if (taskList.size() <= 0) {
                NextTaskInfo result = new NextTaskInfo();
                result.setProIsEnd("Y");
                list.add(result);
                return Result.success(list);
            }

            taskList.forEach(ta -> {
                NextTaskInfo result = new NextTaskInfo();
                result.setTaskDefineKey(ta.getTaskDefinitionKey());
                result.setTaskText(ta.getName());
                result.setProcInstId(ta.getProcessInstanceId());
                result.setTaskAssign(ta.getAssignee());
//                result.setProIsEnd(procIsEnd(ta.getProcessInstanceId()) ? "Y" : "N");
                result.setProIsEnd("N");
                list.add(result);
            });
            return Result.success(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error(1, "完成任务失败" + taskId);
    }

    /**
     * 撤销整个流程
     *
     * @param proId
     * @return
     */
    @RequestMapping("/repealPro")
    public Result<String> repealPro(@RequestParam String proId) {
        runtimeService.deleteProcessInstance(proId, "撤销流程");
        return Result.success("撤销成功");
    }

    /**
     * 单步撤销
     *
     * @param proInstId    流程实例Id
     * @param toBackNoteId 格式：sid-26585B1A-9680-4331-AD31-7A107BA03AB7
     * @return
     */
    @RequestMapping("/singleRepealPro")
    public Result<String> singleRepealPro(@RequestParam String proInstId, @RequestParam String toBackNoteId) {
        try {
            // 根据流程实例找到当前任务节点
            List<Task> tasks = taskService.createTaskQuery().processInstanceId(proInstId).list();
            for (Task t : tasks) {
                String currentTaskId = processGoBack.turnBackNew(t.getId(), "流程单步撤销", "", toBackNoteId);
            }

            return Result.success("撤销成功");

        } catch (Exception e) {
            log.error("", e);
        }
        return Result.error(1, "单步撤销失败");
    }

    /**
     * 委托任务,a指派b办理，b办理之后回到a，然后a办理之后流程继续往下
     *
     * @param taskId 任务id
     * @param owner  被委托人
     * @return
     */
    @PostMapping("/ownerTask")
    public Result<String> ownerTask(String taskId, String owner) {
        taskService.delegateTask(taskId, owner);
        return Result.success();
    }

}

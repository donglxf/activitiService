package com.ht.commonactivity.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ht.commonactivity.common.ActivitiConstants;
import com.ht.commonactivity.common.ModelParamter;
import com.ht.commonactivity.common.RpcDeployResult;
import com.ht.commonactivity.common.RpcStartParamter;
import com.ht.commonactivity.common.enumtype.ActivitiSignEnum;
import com.ht.commonactivity.common.result.PageResult;
import com.ht.commonactivity.common.result.Result;
import com.ht.commonactivity.entity.ActModelDefinition;
import com.ht.commonactivity.entity.ActProcRelease;
import com.ht.commonactivity.entity.ActProcessJumpHis;
import com.ht.commonactivity.rpc.UcAppRpc;
import com.ht.commonactivity.service.*;
import com.ht.commonactivity.utils.TestPointCat;
import com.ht.commonactivity.vo.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.Model;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @create 2017-06-06 13:34
 */
@RestController
@Log4j2
public class ActivitiAuthorityController implements ModelDataJsonConstants {

    @Resource
    protected RepositoryService repositoryService;

    @Resource
    protected ObjectMapper objectMapper;
    @Resource
    protected ActivitiService activitiService;

    /**
     * 获取模型信息
     *
     * @param paramter
     * @return
     */
    @RequestMapping(value = "/getModelInfo")
    @ApiIgnore
    public Result<ModelParamter> getModelInfo(@RequestBody ModelParamter paramter) {
        log.info("获取模型信息,参数paramter:" + JSON.toJSONString(paramter));
        Result<ModelParamter> data = null;
        try {
            if (paramter == null || StringUtils.isEmpty(paramter.getModelId())) {
                log.error("获取模型失败：参数异常，模型ID为空！");
                data = Result.error(1, "参数异常，模型ID为空！");
                return data;
            }
            Model model = activitiService.getModelInfo(paramter.getModelId());
            if (model == null) {
                log.error("获取模型失败：没有对应的模型！");
                data = Result.error(1, "没有对应的模型！");
                return data;
            }
            paramter.setModelId(model.getId());
            paramter.setName(model.getName());
            paramter.setKey(model.getKey());
            paramter.setCategory(model.getCategory());
            data = Result.success(paramter);
        } catch (Exception e) {
            log.error("获取模型失败：", e);
            data = Result.error(1, "获取模型失败");
        }
        log.info("获取模型信息！");
        return data;
    }


    @RequestMapping("/editModel")
    public ObjectNode getEditorJson(ModelParamterVo paramter) {
        log.info("getEditorJson invoke start ,paramter:" + JSON.toJSONString(paramter));
        ObjectNode modelNode = null;
        Model model = repositoryService.getModel(paramter.getModelId());
        if (model != null) {
            try {
                if (StringUtils.isNotEmpty(model.getMetaInfo())) {
                    modelNode = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
                } else {
                    modelNode = objectMapper.createObjectNode();
                    modelNode.put(MODEL_NAME, model.getName());
                }
                modelNode.put(MODEL_ID, model.getId());
                ObjectNode editorJsonNode = (ObjectNode) objectMapper.readTree(
                        new String(repositoryService.getModelEditorSource(model.getId()), "utf-8"));
                modelNode.put("model", editorJsonNode);
            } catch (Exception e) {
                log.error("Error creating model JSON", e);
                throw new ActivitiException("Error creating model JSON", e);
            }
        }
        return modelNode;
    }

    @RequestMapping(value = "/model/save", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void saveModel(@RequestParam String modelId, @RequestBody MultiValueMap<String, String> values) {
        log.info("saveModel invoke ,paramter[modelId:" + modelId + ";values:" + JSON.toJSONString(values));
        List<String> list = values.get("json_xml");
        String json = list.get(0);
        Map<String, Object> map = new GsonJsonParser().parseMap(json);
        activitiService.createRuleTask(map);
        values.set("json_xml", JSON.toJSONString(map));
        try {
            activitiService.saveModel(modelId, values);
        } catch (Exception e) {
            log.error("Error saving model", e);
            throw new ActivitiException("Error saving model", e);
        }
        log.info("模型定义保存成功！");
    }

    @RequestMapping(value = "/editor/stencilset")
    public String getStencilset() {
        InputStream stencilsetStream = this.getClass().getClassLoader().getResourceAsStream("stencilset.json");
        try {
            return IOUtils.toString(stencilsetStream, "utf-8");
        } catch (Exception e) {
            throw new ActivitiException("Error while loading stencil set", e);
        }
    }


}
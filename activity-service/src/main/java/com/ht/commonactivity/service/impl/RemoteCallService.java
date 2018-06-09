package com.ht.commonactivity.service.impl;

import com.alibaba.fastjson.JSON;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service("remoteCallService")
public class RemoteCallService implements JavaDelegate {

    private Expression url;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String urls = (String) url.getValue(execution);
        execution.getVariables(); // 获取流程变量
        RestTemplate client = new RestTemplate();
        Map<String, Object> ma = restTemplate.getForObject(urls, Map.class);
        execution.setVariables(ma);
        System.out.println(JSON.toJSONString(ma));
    }
}

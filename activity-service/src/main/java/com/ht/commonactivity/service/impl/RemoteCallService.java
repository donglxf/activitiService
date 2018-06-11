package com.ht.commonactivity.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service("remoteCallService")
@Log4j2
public class RemoteCallService implements JavaDelegate {

    private Expression url;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String urls = (String) url.getValue(execution); // http://risk-drools/proTest
        execution.getVariables(); // 获取流程变量
        Map<String, Object> map = new HashMap<>();
        map.put("clientFlag", "传参");
        ResponseEntity<Map> ma = restTemplate.postForEntity(urls, map, Map.class);
        execution.setVariables(ma.getBody());
        log.info("远程接口返回数据：===" + JSON.toJSONString(ma));
    }
}

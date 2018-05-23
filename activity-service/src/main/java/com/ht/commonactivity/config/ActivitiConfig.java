package com.ht.commonactivity.config;

import com.ht.commonactivity.utils.DistributedIdGenerator;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.impl.persistence.deploy.Deployer;
import org.activiti.engine.impl.rules.RulesDeployer;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ActivitiConfig {

    @Autowired
    private SpringProcessEngineConfiguration springProcessEngineConfiguration;

    @Bean
    public ProcessEngineConfiguration setProcessEngineConfiguration() {
        DistributedIdGenerator distributedIdGenerator = new DistributedIdGenerator();
        springProcessEngineConfiguration.setIdGenerator(distributedIdGenerator);
//        List<Deployer> list=new ArrayList<Deployer>();
//        list.add(getRuleDeploy());
//        springProcessEngineConfiguration.setCustomPostDeployers(list);
        return springProcessEngineConfiguration.getProcessEngineConfiguration();
    }

//    @Bean
//    public SpringProcessEngineConfiguration setSpringProcessEngineConfiguration(){
//        SpringProcessEngineConfiguration configuration=new SpringProcessEngineConfiguration();
//        List<Deployer> list=new ArrayList<Deployer>();
//        list.add(getRuleDeploy());
//        configuration.setCustomPostDeployers(list);
//        configuration.setTransactionManager(null);
//        return configuration;
//    }

//    @Bean
//    public StandaloneProcessEngineConfiguration getStandlone() {
//        StandaloneProcessEngineConfiguration a = new StandaloneProcessEngineConfiguration();
//        List<Deployer> li = new ArrayList();
//        li.add(getRuleDeploy());
//        a.setCustomPostDeployers(li);
//        return a;
//    }
//
//    @Bean
//    public RulesDeployer getRuleDeploy() {
//        return new RulesDeployer();
//    }

}

package com.ht.commonactivity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.builder.SpringApplicationBuilder;

@EnableFeignClients(basePackages = {"com.ht.ussp.client", "com.ht.commonactivity.rpc"})
@MapperScan("com.ht.commonactivity.mapper")
@EnableTransactionManagement
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"com.ht.commonactivity", "com.ht.ussp.bean"})
public class CommonActivityApplication {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(CommonActivityApplication.class, args);
//        new SpringApplicationBuilder(CommonActivityApplication.class).web(true).run(args);
        System.err.println("ヾ(◍°∇°◍)ﾉﾞ    CommonActivityApplication启动成功      ヾ(◍°∇°◍)ﾉﾞ\n");

    }
}

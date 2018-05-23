package com.ht.commonactivity.rpc;

import com.ht.commonactivity.common.result.Result;
import com.ht.commonactivity.vo.GetAllAppDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "ussp-uc-app")
public interface UcAppRpc {

    /**
     * 描述：获取所有系统列表
     */
    @PostMapping(value = "/apps/getAllApp", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Result<List<GetAllAppDto>> getAllApp();

}

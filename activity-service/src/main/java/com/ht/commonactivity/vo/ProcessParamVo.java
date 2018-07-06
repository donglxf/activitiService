package com.ht.commonactivity.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class ProcessParamVo {
    @ApiParam("流程实例id")
    private String proInstId;
}

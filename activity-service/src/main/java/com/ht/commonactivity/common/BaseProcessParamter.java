package com.ht.commonactivity.common;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class BaseProcessParamter {

    @ApiParam(value = "系统编码，大写", required = true)
    private String sysCode;
}

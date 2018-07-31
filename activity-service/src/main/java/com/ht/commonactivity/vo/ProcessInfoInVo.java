package com.ht.commonactivity.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.jws.soap.InitParam;

@Data
public class ProcessInfoInVo {
    @ApiParam("系统编码")
    private String sysCode;

    @ApiParam("用于模糊匹配流程编号和名称")
    private String keyWord;

    @ApiParam("开始页数")
    private int limit = 0;
    @ApiParam("每页显示数")
    private int maxSize = 10;
}

package com.ht.commonactivity.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class GetAllTaskByModelCodeVo {
    @ApiParam("模型编码")
    private String modeCode;

    @ApiParam("模型版本")
    private Integer version;
}

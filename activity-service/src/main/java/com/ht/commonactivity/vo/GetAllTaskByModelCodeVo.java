package com.ht.commonactivity.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class GetAllTaskByModelCodeVo {
    @ApiParam("模型编码")
    private String modeCode;
}

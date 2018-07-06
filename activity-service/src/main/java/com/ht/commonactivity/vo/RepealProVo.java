package com.ht.commonactivity.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class RepealProVo {
    @ApiParam("流程实例id")
    private String proInstId;
    @ApiParam("退回节点id")
    private String toBackNoteId;
}

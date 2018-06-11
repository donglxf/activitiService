package com.ht.commonactivity.vo;

import lombok.Data;

import java.util.List;

@Data
public class fileTypeVo {
    private String id;
    private String typeCode;
    private String typeName;
    private int typeLevel;
    private String parentCode;
    private String typePath;
    private int orderNo;
    private List<fileTypeVo> children;
}

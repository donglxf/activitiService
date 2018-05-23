package com.ht.commonactivity.vo;

import lombok.Data;

@Data
public class GetAllAppDto {
    private String id;
    private String app;
    private String createOperator;
    private String createdDatetime;
    private String delFlag;
    private String jpaVersion;
    private String lastModifiedDatetime;
    private String name;
    private String nameCn;
    private String status;
    private String updateOperator;

}

package com.ht.commonactivity.service;

/**
 *  全局订单编号生成器
 */
public interface ModelCodeGenerateService {


    /**
     * 模型编号
     * @return
     */
    public String getModelCode(String sysCode,String typeCode) throws Exception;

    /**
     * 类型编号
     * @return
     */
    public String getTypeCode(String sysCode) throws Exception;
}

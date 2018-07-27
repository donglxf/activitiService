package com.ht.commonactivity.service.impl;

import com.ht.commonactivity.service.ModelCodeGenerateService;
import com.ht.framework.generator.service.IdSequenceService;
import com.ht.framework.tool.format.SeqFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 编码生成服务类
 */
@Service
public class ModelCodeGenerateServiceImpl implements ModelCodeGenerateService {

    @Autowired
    private IdSequenceService sequenceService;

    /**
     * 档案编号
     *
     * @return
     */
    @Override
    public String getModelCode(String sysCode, String typeCode) throws Exception {
        String modelId = "modelCode";
        String seqid = sequenceService.nextValue(modelId);
        String preFix = "WF" + "-" + sysCode.toUpperCase() + "-" + typeCode.toUpperCase();
        return SeqFormat.getYyyyMMddSeq(new Date(), Long.parseLong(seqid));
//        return SeqFormat.getPrefixSeq(preFix, Long.parseLong(seqid));
    }

    @Override
    public String getTypeCode(String sysCode) throws Exception {
        String modelId = "typeCode";
        String seqid = sequenceService.nextValue(modelId);
        return SeqFormat.getPrefixYyyyMMddSeq(sysCode, new Date(), Long.parseLong(seqid));
    }

}



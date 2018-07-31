package com.ht.commonactivity.service;


import com.ht.commonactivity.common.service.BaseService;
import com.ht.commonactivity.entity.ActModelDefinition;
import com.ht.commonactivity.vo.ProcessInfoInVo;
import com.ht.commonactivity.vo.ProcessInfoOutVo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author dyb
 * @since 2018-04-09
 */
public interface ActModelDefinitionService extends BaseService<ActModelDefinition> {

    List<ProcessInfoOutVo> getProcessInfo(ProcessInfoInVo vo);
}

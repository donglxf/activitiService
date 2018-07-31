package com.ht.commonactivity.service.impl;

import com.ht.commonactivity.common.service.impl.BaseServiceImpl;
import com.ht.commonactivity.entity.ActModelDefinition;
import com.ht.commonactivity.mapper.ActModelDefinitionMapper;
import com.ht.commonactivity.service.ActModelDefinitionService;
import com.ht.commonactivity.vo.ProcessInfoInVo;
import com.ht.commonactivity.vo.ProcessInfoOutVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author dyb
 * @since 2018-04-09
 */
@Service
public class ActModelDefinitionServiceImpl extends BaseServiceImpl<ActModelDefinitionMapper, ActModelDefinition> implements ActModelDefinitionService {

    @Autowired
    private ActModelDefinitionMapper actModelDefinitionMapper;

    @Override
    public List<ProcessInfoOutVo> getProcessInfo(ProcessInfoInVo vo) {
        return actModelDefinitionMapper.getProcessInfo(vo);
    }
}

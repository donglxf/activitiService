package com.ht.commonactivity.mapper;


import com.ht.commonactivity.common.mapper.SuperMapper;
import com.ht.commonactivity.entity.ActModelDefinition;
import com.ht.commonactivity.vo.ProcessInfoInVo;
import com.ht.commonactivity.vo.ProcessInfoOutVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author dyb
 * @since 2018-04-09
 */
public interface ActModelDefinitionMapper extends SuperMapper<ActModelDefinition> {

    List<ProcessInfoOutVo> getProcessInfo(ProcessInfoInVo vo);
}

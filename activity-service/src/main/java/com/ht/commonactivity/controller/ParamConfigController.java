package com.ht.commonactivity.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ht.commonactivity.common.enumtype.ParamConfigEnum;
import com.ht.commonactivity.common.result.Result;
import com.ht.commonactivity.entity.ActivitiFileType;
import com.ht.commonactivity.entity.dto.FileTypeDto;
import com.ht.commonactivity.rpc.UcAppRpc;
import com.ht.commonactivity.service.ActivitiFileTypeService;
import com.ht.commonactivity.utils.UUIDUtils;
import com.ht.commonactivity.vo.GetAllAppDto;
import com.ht.ussp.util.DateUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Log4j2
@RequestMapping("/fileIndex")
public class ParamConfigController {

    @Autowired
    protected UcAppRpc ucAppRpc;

    @Autowired
    private ActivitiFileTypeService activitiFileTypeService;

    @GetMapping("/getAll")
    @ApiOperation(value = "查询所有的对象")
    public Result<List<ActivitiFileType>> getAlls(String fileTypeCode) {
        Wrapper<ActivitiFileType> wrapper = new EntityWrapper<>();
        wrapper.eq("parent_code", fileTypeCode);
        List<ActivitiFileType> list = activitiFileTypeService.selectList(wrapper);
        // Page<EntityInfo> page = new Page<>();
        // page = entityInfoService.selectPage(page);

        return Result.success(list);
    }

    @RequestMapping("/addChildrenNode")
    @ApiOperation("添加子节点")
    public Result<String> addChildrenNode(ActivitiFileType fileType) {
        String fileTypeCode = UUIDUtils.getNextId();
        fileType.setFileTypeCode(fileTypeCode);
        String typePath = fileType.getFiltTypePath();
        String[] path = new String[0];
        if (StringUtils.isNotBlank(typePath)) {
            path = typePath.split("/");
            fileType.setLfileTypeLevel(path.length + 1);
            fileType.setFiltTypePath(typePath + "/" + fileTypeCode);
            fileType.setParentCode(path[path.length - 1]);
        } else { //  filtTypePath==null 标识新增顶级菜单
            fileType.setLfileTypeLevel(1);
            fileType.setFiltTypePath(fileTypeCode);
            fileType.setParentCode(ParamConfigEnum.top.getVal());
        }

        fileType.setCreateTime(DateUtil.getDate(DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()), "yyyy-MM-dd HH:mm:ss"));
        // 检测同层级菜单是否有重复名和code
        List<ActivitiFileType> li = activitiFileTypeService.selectList(new EntityWrapper<ActivitiFileType>().eq("lfile_type_level", path.length + 1).eq("file_type_name", fileType.getFileTypeName()));
        if (null != li && li.size() > 0) {
            return Result.error(1, "菜单名重复！");
        }

        activitiFileTypeService.insert(fileType);
        return Result.success();
    }

    @RequestMapping("/editChildrenNode")
    @ApiOperation("编辑子节点")
    public Result<String> editChildrenNode(ActivitiFileType fileType) {
        // 检测同层级菜单是否有重复名和code
        Wrapper<ActivitiFileType> wrapper = new EntityWrapper<ActivitiFileType>();
        wrapper.eq("lfile_type_level", fileType.getLfileTypeLevel());
        wrapper.eq("file_type_name", fileType.getFileTypeName());
        wrapper.ne("id", fileType.getId());
        List<ActivitiFileType> li = activitiFileTypeService.selectList(wrapper);
        if (null != li && li.size() > 0) {
            return Result.error(1, "菜单名重复！");
        }
        fileType.setUpdateTime(DateUtil.getDate(DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()), "yyyy-MM-dd HH:mm:ss"));
        activitiFileTypeService.updateById(fileType);
        return Result.success();
    }

    @RequestMapping("/deleteChildrenNode")
    @ApiOperation("删除子节点")
    public Result<String> deleteChildrenNodes(ActivitiFileType fileType) {
        activitiFileTypeService.delete(new EntityWrapper<ActivitiFileType>().like("filt_type_path", fileType.getFileTypeCode()));
        return Result.success();
    }

    @GetMapping("/getInfoById")
    @ApiOperation(value = "通过id查询详细信息")
    public Result<ActivitiFileType> getDateById(Long id) {
        ActivitiFileType entityInfo = activitiFileTypeService.selectById(id);
        return Result.success(entityInfo);
    }

    @RequestMapping("/fileTypeList")
    @ApiOperation("树初始加载")
    public List<FileTypeDto> fileTypeList() {
        // 获取uc所有系统
        List<GetAllAppDto> newList = new ArrayList<>();
        Result<List<GetAllAppDto>> result = ucAppRpc.getAllApp();
        List<GetAllAppDto> dto = result.getData();
        // 与当前所有二级菜单匹配
        List<ActivitiFileType> tlist = activitiFileTypeService.selectList(new EntityWrapper<ActivitiFileType>().eq("lfile_type_level", "2"));
        // 判断接口数据是否在表里面存在，如果不存在就insert，存在则保持不变
        for (int i = 0; i < dto.size(); i++) {
            GetAllAppDto d = dto.get(i);
            boolean bool = false;
            for (int j = 0; j < tlist.size(); j++) {
                ActivitiFileType type = tlist.get(j);
                if (d.getApp().equals(type.getFileTypeCode())) {
                    bool = true;
                    break;
                }
            }
            if (!bool) {
                newList.add(d);
            }
        }
        newList.forEach(li -> {
            ActivitiFileType t = new ActivitiFileType();
            t.setFileTypeCode(li.getApp());
            t.setParentCode("001");
            t.setLfileTypeLevel(2);
            t.setFileTypeName(li.getNameCn());
            t.setFiltTypePath("001/" + li.getApp());
            activitiFileTypeService.insert(t);
        });


//        Result<List<FileTypeDto>> result = null;
        List<FileTypeDto> fileTypes = new ArrayList<FileTypeDto>();

        List<ActivitiFileType> list = activitiFileTypeService.selectList(new EntityWrapper<ActivitiFileType>());
        list.forEach(li -> {
            FileTypeDto dto1 = new FileTypeDto(li.getFileTypeCode(), li.getFileTypeName(), li.getParentCode(), li.getFiltTypePath());
            fileTypes.add(dto1);
        });
//        result = Result.success(fileTypes);
        return fileTypes;
    }
}

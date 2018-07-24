package com.ht.commonactivity.controller;

import com.ht.commonactivity.common.result.Result;
import com.ht.commonactivity.service.ModelCallLogService;
import com.ht.commonactivity.utils.DateUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 报表统计
 * </p>
 *
 * @author dyb
 * @since 2018-07-23
 */
@RestController
@RequestMapping("/report")
@Log4j2
public class FileReportStatisticsController extends BaseController {

    @Autowired
    private ModelCallLogService modelCallLogService;

//    @PostMapping("/statistic")
//    @ApiOperation(value = "")
//    public Result<Map<String, Object>> statistic(HttpServletRequest request) {
//        Map<String, String[]> paramMap = request.getParameterMap();
//        Map<String, Object> map = new HashMap<>();
//        if (null != paramMap && paramMap.size() > 0) {
//            String startTime = StringUtils.isNotBlank(paramMap.get("startDate")[0]) ? paramMap.get("startDate")[0] : null;
//            String endTime = StringUtils.isNotBlank(paramMap.get("endDate")[0]) ? paramMap.get("endDate")[0] : null;
//            map.put("startTime", startTime);
//            map.put("endTime", endTime);
//        } else {
//            Date curentDate = DateUtils.getDate("yyyy-MM-dd");
//            Date beforeDate = DateUtils.addDays(curentDate, -30);
//            log.info(DateUtils.getDateString(beforeDate) + ">>>>>>>>>>>>.");
//            map.put("startTime", DateUtils.getDateString(beforeDate));
//            map.put("endTime", DateUtils.getDateString(curentDate));
//        }
//        Map<String, Object> resultMap = modelCallLogService.reportStatistics(map);
//        return Result.success(resultMap);
//    }
//
//    @PostMapping("/pie")
//    @ApiOperation(value = "档案状态统计图")
//    public Result<List<Map<String, Object>>> pie(HttpServletRequest request) {
//        Map<String, String[]> paramMap = request.getParameterMap();
//        Map<String, Object> map = new HashMap<>();
//        if (null != paramMap && paramMap.size() > 0) {
//            String companyName = StringUtils.isNotBlank(paramMap.get("companyName")[0]) ? paramMap.get("companyName")[0] : null;
//            String archivesType = StringUtils.isNotBlank(paramMap.get("archivesType")[0]) ? paramMap.get("archivesType")[0] : null;
//            map.put("companyName", companyName);
//            map.put("archivesType", archivesType);
//        }
//        List<Map<String, Object>> resultMap = modelCallLogService.pieStatistics(map);
//        return Result.success(resultMap);
//    }
//
//    @PostMapping("/fileTypeStatus")
//    @ApiOperation(value = "档案类型状态统计图")
//    public Result<Map<String, Object>> fileTypeStatus(HttpServletRequest request) {
//        Map<String, String[]> paramMap = request.getParameterMap();
//        Map<String, Object> map = new HashMap<>();
//        if (null != paramMap && paramMap.size() > 0) {
//            String startTime = StringUtils.isNotBlank(paramMap.get("startDate")[0]) ? paramMap.get("startDate")[0] : null;
//            String endTime = StringUtils.isNotBlank(paramMap.get("endDate")[0]) ? paramMap.get("endDate")[0] : null;
//            map.put("startTime", startTime);
//            map.put("endTime", endTime);
//        } else {
//            Date curentDate = DateUtils.getDate("yyyy-MM-dd");
//            Date beforeDate = DateUtils.addDays(curentDate, -30);
//            log.info(DateUtils.getDateString(beforeDate) + ">>>>>>>>>>>>.");
//            map.put("startTime", DateUtils.getDateString(beforeDate));
//            map.put("endTime", DateUtils.getDateString(curentDate));
//        }
//
//        Map<String, Object> resultMap = modelCallLogService.typeStatistics(map);
//        return Result.success(resultMap);
//    }

    @PostMapping("/indexLine")
    @ApiOperation(value = "首页折线图")
    public Result<Map<String, Object>> indexLine(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> resultMap = modelCallLogService.indexLine(map);
        return Result.success(resultMap);
    }

    @PostMapping("/subBar")
    @ApiOperation(value = "首页柱状图")
    public Result<Map<String, Object>> indexBar(HttpServletRequest request) {
        Map<String, String[]> paramMap = request.getParameterMap();
        Map<String, Object> map = new HashMap<>();
        if (null != paramMap && paramMap.size() > 0) {
            String date = StringUtils.isNotBlank(paramMap.get("date")[0]) ? paramMap.get("date")[0] : null;
            map.put("date", date);
        }
        Map<String, Object> resultMap = modelCallLogService.indexBar(map);
        return Result.success(resultMap);
    }


}


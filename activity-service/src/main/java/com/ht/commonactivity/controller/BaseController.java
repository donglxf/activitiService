package com.ht.commonactivity.controller;

import com.ht.ussp.bean.LoginUserInfoHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 说明：
 *
 * @auther 张鹏
 * @create
 */
public class BaseController {
    @Autowired
    public LoginUserInfoHelper userInfoHelper;


    public String getUserId() {
        String userId = userInfoHelper.getUserId();
        return userId = StringUtils.isEmpty(userId) ? "-1" : userId;
    }
    public long getUserId4Long() {
        String userId = userInfoHelper.getUserId();
         userId = StringUtils.isEmpty(userId) ? "-1" : userId;
        return Long.parseLong(userId);
    }

}

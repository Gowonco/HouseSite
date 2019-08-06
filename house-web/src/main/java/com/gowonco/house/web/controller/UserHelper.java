package com.gowonco.house.web.controller;

import com.gowonco.house.common.model.User;
import com.gowonco.house.common.result.ResultMsg;
import org.apache.commons.lang3.StringUtils;

/**
 * 用户验证
 *
 * @author gowonco
 * @date 2019-07-24 14:04
 */
public class UserHelper {

    /**
     * 验证注册信息
     * @param account
     * @return 验证结果
     */
    public static ResultMsg validate(User account) {
        if (StringUtils.isBlank(account.getEmail()))
            return ResultMsg.errorMsg("邮箱有误");
        if (StringUtils.isBlank(account.getName()))
            return ResultMsg.errorMsg("名字有误");
        if (StringUtils.isBlank(account.getPasswd()) || StringUtils.isBlank(account.getConfirmPasswd())
                || !account.getPasswd().equals(account.getConfirmPasswd()))
            return ResultMsg.errorMsg("密码有误");
        if (account.getPasswd().length()<6)
            return ResultMsg.errorMsg("密码要大于6位");
        return ResultMsg.successMsg("");
    }
}

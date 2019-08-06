package com.gowonco.house.web.controller;

import com.gowonco.house.biz.service.UserService;
import com.gowonco.house.common.constants.CommonConstants;
import com.gowonco.house.common.model.User;
import com.gowonco.house.common.result.ResultMsg;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author gowonco
 * @date 2019-07-16 0:14
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @RequestMapping(value = "/str", method = RequestMethod.GET)
    @ResponseBody
    public String getStr() {
        return "ssss";
    }

    /**
     * 注册
     * @param account 用户
     * @param modelMap 模型
     * @return 视图
     */
    @RequestMapping("accounts/register")
    public String accountsRegister(User account, ModelMap modelMap) {
        // 用户名为空直接返回注册页面
        if (account == null || account.getName() == null) {
            return "/user/account/register";
        }
        ResultMsg resultMsg = UserHelper.validate(account);
        if (resultMsg.isSuccess() && userService.addAccount(account)) {
            modelMap.put("email", account.getEmail());
            return "/user/account/registerSubmit";
        } else {
            return "redirect:/accounts/register?" + resultMsg.asUrlParams();
        }
    }

    /**
     * 激活验证
     * @param key 激活的key
     * @return 视图
     */
    @RequestMapping("accounts/verify")
    public String verify(String key) {
        boolean result = userService.enable(key);
        if (result) {
            return "redirect:/index?" + ResultMsg.successMsg("激活成功").asUrlParams();
        } else {
            return "redirect:accounts/register?" + ResultMsg.errorMsg("激活失败，请确认链接是否过期").asUrlParams();
        }
    }

    /**
     * 登录
     * @param req http请求
     * @return 视图
     */
    @RequestMapping("accounts/signin")
    public String sign(HttpServletRequest req) {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String target = req.getParameter("target");
        if (username == null || password == null) {
            req.setAttribute("target", target);
            return "user/account/signin";
        }
        User user = userService.auth(username, password);
        if (user == null) {
            return "redirect:/accounts/signin?target=" + target + "&username=" + username + "&" + ResultMsg.errorMsg("用户名或密码错误").asUrlParams();
        } else {
            // 设置为true为空时会强制创建一个session
            HttpSession session = req.getSession(true);
            session.setAttribute(CommonConstants.USER_ATTRIBUTE,user);
            session.setAttribute(CommonConstants.PLAIN_USER_ATTRIBUTE,user);
            return StringUtils.isNotBlank(target)?"redirect:"+target:"redirect:/index";
        }
    }

    /**
     * 登出
     * @param req http请求
     * @return 视图
     */
    public String logout(HttpServletRequest req){
        HttpSession session = req.getSession(true);
        session.invalidate();
        return "redirect:/index";
    }

}

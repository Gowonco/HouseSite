package com.gowonco.house.web.interceptor;

import com.google.common.base.Joiner;
import com.gowonco.house.common.constants.CommonConstants;
import com.gowonco.house.common.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 鉴权拦截器
 * @author gowonco
 * @date 2019-07-31 0:32
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String,String[]> map = request.getParameterMap();
        map.forEach((k,v)->{
            if("errorMsg".equals(k) || "successMsg".equals(k) ||"target".equals(k)){
                request.setAttribute(k, Joiner.on(",").join(v));
            }
        });

        String reqUri = request.getRequestURI();
        if(reqUri.startsWith("/static") || reqUri.startsWith("/error")){
            return  true;
        }
        HttpSession session = request.getSession(true);
        User user = (User)session.getAttribute(CommonConstants.USER_ATTRIBUTE);
        if(user != null){
            UserContext.setUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.remove();
    }
}

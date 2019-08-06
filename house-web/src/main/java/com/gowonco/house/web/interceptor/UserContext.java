package com.gowonco.house.web.interceptor;

import com.gowonco.house.common.model.User;

/**
 * @author gowonco
 * @date 2019-07-31 23:11
 */
public class UserContext {
    private static final ThreadLocal<User> USER__HOLDER = new ThreadLocal<>();

    public static  void setUser(User user){
        USER__HOLDER.set(user);
    }

    public static void remove(){
        USER__HOLDER.remove();
    }

    public static User getUser(){
        return USER__HOLDER.get();
    }
}

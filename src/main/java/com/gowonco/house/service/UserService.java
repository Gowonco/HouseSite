package com.gowonco.house.service;

import com.gowonco.house.common.model.User;
import com.gowonco.house.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gowonco
 * @date 2019-07-16 0:16
 */
@Service
@Component
public class UserService {

    @Autowired
    private UserMapper userMapper;
    public List<User> getUsers() {
        return  userMapper.selectUsers();
    }

}

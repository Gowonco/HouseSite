package com.gowonco.house.mapper;

import com.gowonco.house.common.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户Jdbc
 * @author gowonco
 * @date 2019-07-16 0:01
 */
@Mapper
@Component
public interface UserMapper {

    List<User> selectUsers();
}


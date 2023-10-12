package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author M
 * @version 1.0
 * @description:
 * @date 2023/10/11 23:28
 */

@Mapper
public interface UserMapper {


    @Select("select * from user where id=#{openid}")
    User getByopenid(String openid);


    void insert(User user);
}

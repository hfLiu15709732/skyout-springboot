package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.vo.UserLoginVO;
import org.springframework.stereotype.Service;

/**
 * @author M
 * @version 1.0
 * @description:
 * @date 2023/10/11 22:49
 */

public interface UserService {

    User wxLogin(UserLoginDTO userLoginDTO);
}

package com.sky.service.impl;

import com.sky.service.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * @author M
 * @version 1.0
 * @description:
 * @date 2023/10/11 12:54
 */

@Slf4j
@Service
public class ShopServiceImpl implements ShopService {


    public static final String SHOP_KEY="SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void setStatus(String status) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(SHOP_KEY,status);
    }


    @Override
    public String getStatus() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String status =(String) valueOperations.get(SHOP_KEY);
        return status;
    }
}

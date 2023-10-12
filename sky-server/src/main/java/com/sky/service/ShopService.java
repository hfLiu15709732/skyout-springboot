package com.sky.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author M
 * @version 1.0
 * @description:
 * @date 2023/10/11 12:52
 */
public interface ShopService
{

    void setStatus(String status);

    String getStatus();
}

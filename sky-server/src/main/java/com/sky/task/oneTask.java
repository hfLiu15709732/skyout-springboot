package com.sky.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author M
 * @version 1.0
 * @description:
 * @date 2023/10/15 20:22
 */


@Component
@Slf4j
public class oneTask {

//    @Scheduled(cron = "0/5 * * * * ?")
    public void testTask1(){
        log.info("定时任务开始了,{}",new Date());
    }
}

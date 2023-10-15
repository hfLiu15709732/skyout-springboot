package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

/**
 * @author M
 * @version 1.0
 * @description:
 * @date 2023/10/15 20:31
 */
@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;


    /**
     * @description:  处理  支付超时订单  的业务函数
     * @author ${USER}
     * @date ${DATE} ${TIME}
     * @version 1.0
     */

    //0/5 是从0秒开始，每个5秒时执行一次
    @Scheduled(cron = "0 * * * * ?")//每分钟触发一次
    public void  processTimeoutOrder(){
        log.info("处理 支付超时订单,{}",new Date());
        LocalDateTime preTome = LocalDateTime.now().plusMinutes(-15);
        List<Orders> ordersList = orderMapper.getByStatusAndOrderTimeLT(Orders.PENDING_PAYMENT, preTome);

        if (ordersList!=null&&ordersList.size()>0){
            //说明存在超时未支付订单，将相关订单取消
            for (Orders orders : ordersList){
                orders.setStatus(Orders.CANCELLED);
                orders.setCancelReason("订单超时未支付！自动取消");
                orders.setCancelTime(LocalDateTime.now());
                orderMapper.update(orders);
            }
        }
    }



    /**
     * @description:  处理一直处于派送中状态的订单
     * @author ${USER}
     * @date ${DATE} ${TIME}
     * @version 1.0
     */
    @Scheduled(cron = "0 0 1 * * ?") //每天凌晨1点触发一次
    public void processDeliveryOrder(){
        log.info("定时处理处于派送中的订单：{}",LocalDateTime.now());

        LocalDateTime time = LocalDateTime.now().plusMinutes(-60);

        List<Orders> ordersList = orderMapper.getByStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS, time);

        if(ordersList != null && ordersList.size() > 0){
            for (Orders orders : ordersList) {
                orders.setStatus(Orders.COMPLETED);
                orderMapper.update(orders);
            }
        }
    }


}

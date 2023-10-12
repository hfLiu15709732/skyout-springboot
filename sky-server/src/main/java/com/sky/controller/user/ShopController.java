package com.sky.controller.user;

import com.sky.result.Result;
import com.sky.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author M
 * @version 1.0
 * @description:
 * @date 2023/10/11 19:00
 */
@RestController(value = "userShopController")
@Slf4j
@RequestMapping("/user/shop")
@Api(tags = "C端-店铺管理相关接口")
public class ShopController {

    @Autowired
    private ShopService shopService;

    /**
     * 查询商家营业状态
     *
     * @param
     * @return
     */
    @GetMapping("/status")
    @ApiOperation("查询商家营业状态")
    public Result<Integer> getStatus() {
        log.info("获取商家营业状态");
        String status1 = shopService.getStatus();
        int statusNum=Integer.parseInt(status1);
        return Result.success(statusNum);
    }
}

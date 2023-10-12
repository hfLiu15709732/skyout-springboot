package com.sky.controller.admin;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.service.ShopService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "adminShopController")
@Slf4j
@RequestMapping("/admin/shop")
@Api(tags = "B端店铺管理相关接口")
public class ShopController {

    @Autowired
    private ShopService shopService;

    /**
     * 修改商家营业状态
     *
     * @param
     * @return
     */
    @PutMapping("/{status}")
    @ApiOperation("修改商家营业状态")
    public Result<String> setStatus(@PathVariable String status) {

        log.info("设置商家营业状态-{}",status.equals("1") ? "营业中": "打烊了");
         shopService.setStatus(status);
        return Result.success();
    }


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

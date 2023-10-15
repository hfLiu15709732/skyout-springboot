package com.sky.controller.user;


import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.service.ShopService;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author M
 * @version 1.0
 * @description:
 * @date 2023/10/13 9:23
 */

@RestController("userShopCartController")
@RequestMapping("/user/shoppingCart")
@Api(tags = "C端-购物车相关接口")
@Slf4j
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 新增购物车
     *
     * @param
     * @return
     */
    @PostMapping ("/add")
    @ApiOperation("添加用户购物车信息")
//
    public Result<List<String>> add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("添加购物车--{}",shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);


        return Result.success();
    }


    /**
     * 查询购物车
     *
     * @param
     * @return
     */
    @GetMapping  ("/list")
    @ApiOperation("查询购物车列表")
//
    public Result<List<ShoppingCart>> list() {
        log.info("查询购物车");
        List<ShoppingCart> cartList = shoppingCartService.showShoppingCart();
        return Result.success(cartList);
    }


    /**
     * 清空购物车
     *
     * @param
     * @return
     */
    @DeleteMapping("/clean")
    @ApiOperation("清空购物车列表")
//
    public Result<String> clean() {
        log.info("清空购物车");
        shoppingCartService.cleanList();
        return Result.success();
    }
}

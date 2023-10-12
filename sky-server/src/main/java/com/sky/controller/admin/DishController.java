package com.sky.controller.admin;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@RequestMapping("/admin/dish")
@Api(tags = "B端菜品管理相关接口")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("")
    @ApiOperation("新增菜品")
    public Result<String> save(@RequestBody DishDTO dishDTO){
        log.info("新增菜品操作-{}",dishDTO);
        dishService.saveDishWithFlavour(dishDTO);



        //清理redis数据缓存
        String redis_Key="dish_"+dishDTO.getCategoryId();
        cleanCatch(redis_Key);

        return Result.success();
    }

    @GetMapping  ("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> save(DishPageQueryDTO dishPageQueryDTO){
        log.info("菜品分页查询-{}",dishPageQueryDTO);

        PageResult pageResult=dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping  ("")
    @ApiOperation("批量删除菜品")
    public Result<String> delete(@RequestParam List<Long> ids){
        log.info("菜品数据批量删除-{}",ids);
        dishService.deleteBatch(ids);

        //由于多个菜品可能在多个分类下，所以删除所有分类下的缓存
        cleanCatch("dish_*");

        return Result.success();
    }

    @GetMapping ("/{id}")
    @ApiOperation("根据ID查询菜品详细信息")
    public Result<DishVO> findDetailById(@PathVariable int id){
        log.info("菜品根据ID查询详细信息-{}",id);
        DishVO dishVO=dishService.findDetailById(id);
        return Result.success(dishVO);
    }

    @PutMapping ("")
    @ApiOperation("根据ID查询菜品详细信息")
    public Result<String> update(@RequestBody DishDTO dishDTO){
        log.info("菜品信息修改-{}",dishDTO);
        dishService.updateWithFlavor(dishDTO);

        //由于菜品可能修改类型，导致两个分类的变更，所以删除所有分类下的缓存
        cleanCatch("dish_*");
        return Result.success();
    }

    /**
     * 菜品起售停售
     *
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("菜品起售停售")
    public Result<String> startOrStop(@PathVariable Integer status, Long id) {
        dishService.startOrStop(status, id);

        //这个获取类型的id还需要查询数据库，所以为了代码不会过渡复杂，还是删除所有分类下的缓存
        cleanCatch("dish_*");

        return Result.success();
    }


    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> list(Long categoryId) {
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }



    private  void  cleanCatch(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}

package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    void insertBatch(List flavorList);

    @Delete("delete from dish_flavor where dish_id=#{dish_id}")
    void deleteById(Long dish_id);

    @Select("select * from dish_flavor where dish_id=#{id};")
    List<DishFlavor> getById(int id);
}

package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@ResponseBody
@Slf4j
@Api(tags = "B端员工管理相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @ApiOperation(value = "员工登录")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @ApiOperation(value = "员工退出")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }


    /**
     * 新增员工
     *
     * @return
     */
    @ApiOperation(value = "新增员工")
    @PostMapping//全局类已经设置了路径，使用restful进行开发
    public Result<String> save(@RequestBody EmployeeDTO employeeDTO) {
        //前端传来的是json，要用@RequestBody
        log.info("新增员工--{}",employeeDTO);//将信息打印，employeeDTO匹配前面的{}
        employeeService.save(employeeDTO);
        return Result.success();
    }


    /**
     * 员工分页查询
     *
     * @return
     */
    @ApiOperation(value = "员工分页查询")
    @GetMapping ("/page")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO) {
        //前端传来的是json，要用@RequestBody
        log.info("员工分页查询--{}",employeePageQueryDTO);//将信息打印，employeeDTO匹配前面的{}
        PageResult pageResult= employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }


    /**
     * 修改员工状态
     *
     * @return
     */
    @ApiOperation(value = "员工状态修改")
    @PostMapping  ("/status/{status}")
    public Result setStatus(@PathVariable(value = "status") Integer status,long id) {
        //前端传来的是json，要用@RequestBody
        log.info("员工状态修改--{}修改为{}",id,status);//将信息打印，employeeDTO匹配前面的{}
        employeeService.setStatus(status,id);
        return Result.success();
    }

    /**
     * 根据Id查询数据
     *
     * @return
     */
    @ApiOperation(value = "根据ID查询员工信息")
    @GetMapping  ("/{id}")
    public Result findById(@PathVariable(value = "id") long id) {
        //前端传来的是json，要用@RequestBody
        log.info("员工信息查询--员工id修改为{}",id);
        Employee employee = employeeService.findById(id);
        return Result.success(employee);
    }

    /**
     * 修改用户信息
     *
     * @return
     */
    @ApiOperation(value = "修改人员信息")
    @PutMapping
    public Result update(@RequestBody EmployeeDTO employeeDTO) {
        //前端传来的是json，要用@RequestBody
        log.info("员工信息修改--{}",employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success();
    }

}

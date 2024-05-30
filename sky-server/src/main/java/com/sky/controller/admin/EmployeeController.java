package com.sky.controller.admin;

import com.github.pagehelper.dialect.ReplaceSql;
import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.DishPageQueryDTO;
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
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工相关接口")
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
    @PostMapping("/login")
    @ApiOperation(value = "员工登录方法")
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
    @PostMapping("/logout")
    @ApiOperation(value = "员工退出登录方法")
    public Result<String> logout() {
        return Result.success();
    }


    @PostMapping
    @ApiOperation(value = "新增员工功能")
    public Result save(@RequestBody EmployeeDTO employeeDTO)
    {
        log.info("新增员工：{}",employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();
    }

    //员工的分页查询
    @ApiOperation(value = "员工分页查询功能")
    @GetMapping("/page")
    public Result<PageResult> query(EmployeePageQueryDTO employeePageQueryDTO)
    {
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }


    //员工账号禁用功能
    @PostMapping("/status/{status}")
    @ApiOperation("员工账号禁用管理")
    public Result startOrStop(@PathVariable Integer status,Long id)
    {
        employeeService.startOrStop(status,id);
        return Result.success();
    }

    //修改员工消息-回显
    @GetMapping("/{id}")
    @ApiOperation("修改员工消息-回显")
    public Result<Employee> getById(@PathVariable long id)
    {
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }

    /**
     * 修改员工消息-修改
     * @return
     */
    @PutMapping
    @ApiOperation("修改员工消息-修改")
    public Result update(@RequestBody EmployeeDTO employeeDTO)
    {
        employeeService.update(employeeDTO);

        return Result.success();
    }





}

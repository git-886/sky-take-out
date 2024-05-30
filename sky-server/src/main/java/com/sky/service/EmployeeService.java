package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 员工添加
     * @param employeeDTO
     * @return
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * 员工数据分页查询
     * @param employeePageQueryDTO
     * @return
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 员工账号禁用管理
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 修改员工消息-回显
     * @param id
     * @return
     */
    Employee getById(Long id);

    /**
     * 修改员工消息-修改
     * @param employeeDTO
     */
    void update(EmployeeDTO employeeDTO);
}

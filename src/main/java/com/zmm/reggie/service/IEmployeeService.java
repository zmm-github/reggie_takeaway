package com.zmm.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zmm.reggie.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zmm.reggie.vo.ResultVo;

import java.util.List;

/**
 * <p>
 * 员工信息 服务类
 * </p>
 *
 * @author zmm
 * @since 2024-09-24
 */
public interface IEmployeeService extends IService<Employee> {
    /**
     * 通过用户名查找指定员工
     *
     * @param username
     * @return
     */
    ResultVo<Employee> findEmployeeByUsername(String username);

    /**
     * 通过token获取当前员工信息
     * @param token
     * @return
     */
    ResultVo<Employee> findEmployeeByToken(String token);

    /**
     * 查询所有员工列表
     * @return
     */
    ResultVo<List<Employee>> findEmployees();

    /**
     * 根据员工名模糊查询员工信息
     * @param nickName
     * @return
     */
    ResultVo<List<Employee>> likeSearchEmployeeByName(String nickName);

    /**
     * 新增员工
     * @param employee
     * @param token
     * @return
     */
    ResultVo addEmployee(Employee employee, String token);

    /**
     * 根据员工ID删除员工信息
     * @param id
     * @return
     */
    ResultVo delEmployeeById(Integer id);

    /**
     * 根据员工Id更新员工信息
     * @param employee
     * @param token
     * @return
     */
    ResultVo updateEmployeeById(Employee employee, String token);

    /**
     * 获取当前登录员工的角色列表
     * @param employeeId
     * @return
     */
    ResultVo getLoginEmployeeRoles(Integer employeeId);
}

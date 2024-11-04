package com.zmm.reggie.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zmm.reggie.entity.Employee;
import com.zmm.reggie.service.IEmployeeService;
import com.zmm.reggie.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 员工信息 前端控制器
 * </p>
 *
 * @author zmm
 * @since 2024-09-24
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    /**
     * 通过用户名查找指定员工
     *
     * @param username
     * @return
     */
    @GetMapping("/findEmployeeByUsername")
    public ResultVo<Employee> findEmployeeByUsername(@RequestParam("username") String username) {
        return employeeService.findEmployeeByUsername(username);
    }

    /**
     * 根据token获取当前员工信息
     *
     * @param token
     * @return
     */
    @GetMapping("/currentEmployeeInfo")
    public ResultVo<Employee> findEmployeeByToken(@RequestHeader("authorization") String token) {
        return employeeService.findEmployeeByToken(token);
    }

    /**
     * 查询所有员工列表
     * @return
     */
    @GetMapping("/findEmployees")
    public ResultVo<List<Employee>> findEmployees() {
        return employeeService.findEmployees();
    }

    /**
     * 根据员工名模糊查询员工信息
     * @param nickName
     * @return
     */
    @GetMapping("/likeSearchEmployeeByName")
    public ResultVo<List<Employee>> likeSearchEmployeeByName(@RequestParam(required = false, value = "nickName") String nickName) {
        return employeeService.likeSearchEmployeeByName(nickName);
    }

    /**
     * 新增员工
     * @param employee
     * @param token
     * @return
     */
    @PostMapping("/addEmployee")
    public ResultVo addEmployee(@RequestBody Employee employee, @RequestHeader("authorization") String token) {
        return employeeService.addEmployee(employee, token);
    }

    /**
     * 根据员工ID删除员工信息
     * @param id
     * @return
     */
    @DeleteMapping("/delEmployeeById/{id}")
    public ResultVo delEmployeeById(@PathVariable Integer id) {
        return employeeService.delEmployeeById(id);
    }

    /**
     * 根据员工ID更新员工信息
     * @param employee
     * @param token
     * @return
     */
    @PutMapping("/updateEmployeeById")
    public ResultVo updateEmployeeById(@RequestBody Employee employee, @RequestHeader("authorization") String token) {
        return employeeService.updateEmployeeById(employee, token);
    }

    /**
     * 获取当前登录员工的角色列表
     * @param employeeId
     * @return
     */
    @GetMapping("/getLoginEmployeeRoles")
    public ResultVo getLoginEmployeeRoles(Integer employeeId) {
        return employeeService.getLoginEmployeeRoles(employeeId);
    }

}

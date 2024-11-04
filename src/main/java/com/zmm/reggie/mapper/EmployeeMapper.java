package com.zmm.reggie.mapper;

import com.zmm.reggie.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 员工信息 Mapper 接口
 * </p>
 *
 * @author zmm
 * @since 2024-09-24
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    List<String> getRoleNameByEmployeeId(Integer employeeId);
}

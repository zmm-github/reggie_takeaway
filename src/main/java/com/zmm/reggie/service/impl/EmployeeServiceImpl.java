package com.zmm.reggie.service.impl;

import com.zmm.reggie.entity.Employee;
import com.zmm.reggie.mapper.EmployeeMapper;
import com.zmm.reggie.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 员工信息 服务实现类
 * </p>
 *
 * @author zmm
 * @since 2024-09-24
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}

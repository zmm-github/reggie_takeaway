package com.zmm.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zmm.reggie.common.MyMetaObjectHandler;
import com.zmm.reggie.entity.Employee;
import com.zmm.reggie.entity.EmployeeRole;
import com.zmm.reggie.mapper.EmployeeMapper;
import com.zmm.reggie.mapper.EmployeeRoleMapper;
import com.zmm.reggie.mapper.RoleMapper;
import com.zmm.reggie.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zmm.reggie.utils.JwtUtil;
import com.zmm.reggie.common.ErrorCode;
import com.zmm.reggie.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

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
    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private EmployeeRoleMapper employeeRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 根据用户名查询指定员工信息
     *
     * @param username
     * @return
     */
    @Override
    public ResultVo<Employee> findEmployeeByUsername(String username) {
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, username);
        Employee employee = employeeMapper.selectOne(queryWrapper);
        if (employee != null) {
            return ResultVo.success("查询指定员工成功！", employee);
        } else {
            return ResultVo.fail("查询指定员工失败！");
        }
    }

    /**
     * token校验
     *
     * @param token
     * @return
     */
    public ResultVo<Employee> checkToken(String token) {
        if (!StringUtils.hasLength(token)) {
            return ResultVo.fail(ErrorCode.TOKEN_EMPTY.getCode(), ErrorCode.TOKEN_EMPTY.getMsg());
        }

        Employee employee = JwtUtil.parseToken(token, Employee.class);
        if (employee == null) {
            return ResultVo.fail(ErrorCode.TOKEN_PARSE_FAIL.getCode(), ErrorCode.TOKEN_PARSE_FAIL.getMsg());
        }

        Employee loginEmployee = (Employee) redisTemplate.opsForValue().get("TOKEN_" + token);
        if (loginEmployee == null) {
            return ResultVo.fail(ErrorCode.TOKEN_REDIS_NOR_EXIST.getCode(), ErrorCode.TOKEN_REDIS_NOR_EXIST.getMsg());
        }

        return ResultVo.success("获取当前员工信息成功！", loginEmployee);
    }

    /**
     * 根据token获取当前员工信息
     *
     * @param token
     * @return
     */
    @Override
    public ResultVo<Employee> findEmployeeByToken(String token) {
        /**
         * 1. token合法性校验
         *      是否为空，解析是否成功 redis中是否存在
         * 2. 如果校验失败，返回错误
         * 3. 如果校验成功，返回对应的结果
         */
        return checkToken(token);
    }

    /**
     * 查询所有员工列表
     *
     * @return
     */
    @Override
    public ResultVo<List<Employee>> findEmployees() {
        List<Employee> employees = employeeMapper.selectList(null);
        if (employees != null) {
            return ResultVo.success("分页查询员工列表成功！", employees);
        } else {
            return ResultVo.fail("分页查询员工列表失败！");
        }
    }

    /**
     * 根据员工名模糊查询员工信息
     *
     * @param nickName
     * @return
     */
    @Override
    public ResultVo<List<Employee>> likeSearchEmployeeByName(String nickName) {
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Employee::getNickName, nickName);
        List<Employee> employeeList = employeeMapper.selectList(queryWrapper);
        return ResultVo.success(employeeList);
    }

    /**
     * 新增员工
     *
     * @param employee
     * @param token
     * @return
     */
    @Override
    public ResultVo addEmployee(Employee employee, String token) {
        //新增员工前，首先检查数据库中是否存在该用户名，若存在则提示该用户名已存在，反之继续进行新增操作
        ResultVo<Employee> employeeResultVo = findEmployeeByUsername(employee.getUsername());
        Employee emp = employeeResultVo.getData();
        if (emp != null) {
            return ResultVo.fail("该用户名已存在！");
        }
        // 新增的员工默认密码为123456
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        // 设置创建与更新时间为当前时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        // 根据token获取当前登录用户信息
        Employee loginEmployee = (Employee) redisTemplate.opsForValue().get("TOKEN_" + token);
        // 设置创建与更新人为当前登录用户
        employee.setCreateUser(loginEmployee.getId());
        employee.setUpdateUser(loginEmployee.getId());
        // 设置新增用户默认头像
        employee.setAvatar("https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png");
        int result = employeeMapper.insert(employee);
        // 更新员工角色，新增员工默认为普通用户角色（normal）
        int insert = employeeRoleMapper.insert(new EmployeeRole(null, employee.getId(), 2l));
        if (result > 0 && insert > 0) {
            return ResultVo.success("新增员工成功！");
        } else {
            return ResultVo.fail("新增员工失败！");
        }
    }

    /**
     * 根据员工ID删除员工信息
     *
     * @param id
     * @return
     */
    @Override
    public ResultVo delEmployeeById(Integer id) {
        // 删除员工
        int result = employeeMapper.deleteById(id);
        LambdaQueryWrapper<EmployeeRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EmployeeRole::getEmployeeId, id);
        // 删除员工对应的角色
        int delete = employeeRoleMapper.delete(queryWrapper);
        if (result > 0 && delete > 0) {
            return ResultVo.success("删除员工成功！");
        } else {
            return ResultVo.fail("删除员工失败！");
        }
    }

    /**
     * 根据员工Id更新员工信息
     *
     * @param employee
     * @param token
     * @return
     */
    public ResultVo updateEmployeeById(Employee employee, String token) {
        // 根据token获取登录用户信息
//        Employee loginEmployee = (Employee) redisTemplate.opsForValue().get("TOKEN_" + token);
        // 设置更新人和更新时间
//        employee.setUpdateUser(loginEmployee.getId());
//        employee.setUpdateTime(LocalDateTime.now());
        int result = employeeMapper.updateById(employee);
        if (result > 0) {
            return ResultVo.success("更新员工信息成功！");
        } else {
            return ResultVo.fail("更新员工信息失败！");
        }
    }

    /**
     * 获取当前登录员工的角色列表
     *
     * @param employeeId
     * @return
     */
    @Override
    public ResultVo getLoginEmployeeRoles(Integer employeeId) {
        List<String> roleNameList = employeeMapper.getRoleNameByEmployeeId(employeeId);
//        LambdaQueryWrapper<EmployeeRole> employeeRoleQueryWrapper = new LambdaQueryWrapper<>();
//        employeeRoleQueryWrapper.eq(EmployeeRole::getEmployeeId, employeeId);
//        employeeRoleQueryWrapper.select((SFunction<EmployeeRole, ?>) employeeRoleQueryWrapper, EmployeeRole::getRoleId);
////        employeeRoleQueryWrapper
//        EmployeeRole employeeRole = employeeRoleMapper.selectOne(employeeRoleQueryWrapper);
//        LambdaQueryWrapper<Role> roleQueryWrapper = new LambdaQueryWrapper<>();

        if (roleNameList != null && roleNameList.size() > 0) {
            return ResultVo.success("获取当前登录员工角色列表成功！", roleNameList);
        } else {
            return ResultVo.fail("获取当前登录员工角色列表失败！");
        }
    }

}

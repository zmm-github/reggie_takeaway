package com.zmm.reggie.service.impl;

import com.zmm.reggie.entity.Employee;
import com.zmm.reggie.service.IEmployeeService;
import com.zmm.reggie.service.ILoginService;
import com.zmm.reggie.utils.JwtUtil;
import com.zmm.reggie.common.ErrorCode;
import com.zmm.reggie.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 员工登录 服务实现类
 * </p>
 *
 * @author zmm
 * @since 2024-09-24
 */
@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 员工登录
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public ResultVo accountLogin(String username, String password) {
        /**
         * 1. 检查参数是否合法
         * 2. 如果参数合法，根据用户名去employee表中查询 是否存在
         * 3. 如果不存在 登录失败
         * 4. 如果存在，对页面提交的密码进行md5加密处理，进行密码比对，如果比对失败，则登录失败，否则判断该用户是否被禁用
         * 5、如果被禁用，登录失败，=》该用户已被禁用，无法登录
         * 6、如果未被禁用，使用jwt 生成token 返回给前端
         * 7. token放入redis当中，redis =》 token: user信息 设置过期时间
         * （登录认证的时候，先认证token字符串是否合法，去redis认证是否存在）
         */
        if (!StringUtils.hasLength(username) && !StringUtils.hasLength(password)) {
            return ResultVo.fail(ErrorCode.USERNAME_PWD_EMPTY.getCode(), ErrorCode.USERNAME_PWD_EMPTY.getMsg());
        }

        // 返回的数据
        HashMap<String, Object> data = new HashMap<>();

        // 根据用户名查询员工
        ResultVo<Employee> employeeResultVo = employeeService.findEmployeeByUsername(username);
        Employee employee = employeeResultVo.getData();
        if (employee != null) { // 查询到的员工不为空
            // 对页面提交的密码进行md5加密处理
            password = DigestUtils.md5DigestAsHex(password.getBytes());
            // 进行密码比对
            if (password.equals(employee.getPassword())) {
                if (1 == employee.getStatus()) { // 员工未被禁用
                    // 创建jwt
                    String token = JwtUtil.createToken(employee);

                    // 存储token => redis
                    redisTemplate.opsForValue().set("TOKEN_" + token, employee, 1, TimeUnit.DAYS);
                    System.out.println(token);

                    // 将token信息返回给前端
                    data.put("token", token);
                    return ResultVo.success("登录成功！", data);
                } else {
                    return ResultVo.fail(ErrorCode.USER_DISABLED.getCode(), ErrorCode.USER_DISABLED.getMsg());
                }
            } else {
                return ResultVo.fail(ErrorCode.PWD_NOR_EXIST.getCode(), ErrorCode.PWD_NOR_EXIST.getMsg());
            }
        } else {
            return ResultVo.fail(ErrorCode.USERNAME_PWD_NOR_EXIST.getCode(), ErrorCode.USERNAME_PWD_NOR_EXIST.getMsg());
        }
    }

    @Override
    public ResultVo outLogin(String token) {
        // 退出登录时删除redis中存储的token
        Boolean flag = redisTemplate.delete("TOKEN_" + token);
        if (flag) {
            return ResultVo.success("退出成功！");
        } else {
            return ResultVo.fail("退出失败！");
        }
    }
}

package com.zmm.reggie.interceptor;

import com.alibaba.fastjson.JSON;
import com.zmm.reggie.entity.Employee;
import com.zmm.reggie.common.ErrorCode;
import com.zmm.reggie.utils.BaseContext;
import com.zmm.reggie.vo.ResultVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("authorization");

        // 判断是否为登录请求路径，如果是则直接放行
        if (isLoginRequest(request)) {
            return true;
        }
        // 非登录请求，检查是否有 token 参数，从而判断是否登录
        if (StringUtils.hasLength(token)) { // 已登录 放行
            Employee loginEmployee = (Employee) redisTemplate.opsForValue().get("TOKEN_" + token);
            // 往ThreadLocal中设置当前登录员工ID
            BaseContext.setCurrentId(loginEmployee.getId());
            return true;
        }

        // 未登录 给出提示
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JSON.toJSONString(ResultVo.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg())));
        return false;
    }

    private boolean isLoginRequest(HttpServletRequest request) {
        // 根据你的登录请求路径进行判断，比如 /login 是登录请求路径
        return request.getRequestURI().contains("/login");
    }
}

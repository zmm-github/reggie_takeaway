package com.zmm.reggie.controller;

import com.zmm.reggie.service.ILoginService;
import com.zmm.reggie.vo.LoginParams;
import com.zmm.reggie.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private ILoginService loginService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 后台员工登录
     * @param loginParams
     * @return
     */
    @RequestMapping("/account")
    public ResultVo accountLogin(@RequestBody LoginParams loginParams) {
        String username = loginParams.getUsername();
        String password = loginParams.getPassword();
        return loginService.accountLogin(username, password);
    }

    /**
     * 退出登录
     * @param token
     * @return
     */
    @RequestMapping("/outLogin")
    public ResultVo outLogin(@RequestHeader("authorization") String token) {
        return loginService.outLogin(token);
    }
}

package com.zmm.reggie.service;

import com.zmm.reggie.vo.ResultVo;

/**
 * <p>
 * 员工登录 服务类
 * </p>
 *
 * @author zmm
 * @since 2024-09-23
 */
public interface ILoginService {

    ResultVo accountLogin(String username, String password);

    ResultVo outLogin(String token);
}

package com.zmm.reggie.service.impl;

import com.zmm.reggie.entity.User;
import com.zmm.reggie.mapper.UserMapper;
import com.zmm.reggie.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author zmm
 * @since 2024-09-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}

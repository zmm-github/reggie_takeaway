package com.zmm.reggie.service.impl;

import com.zmm.reggie.entity.Orders;
import com.zmm.reggie.mapper.OrdersMapper;
import com.zmm.reggie.service.IOrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author zmm
 * @since 2024-09-23
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

}

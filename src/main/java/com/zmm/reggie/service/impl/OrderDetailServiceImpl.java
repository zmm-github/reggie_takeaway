package com.zmm.reggie.service.impl;

import com.zmm.reggie.entity.OrderDetail;
import com.zmm.reggie.mapper.OrderDetailMapper;
import com.zmm.reggie.service.IOrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单明细表 服务实现类
 * </p>
 *
 * @author zmm
 * @since 2024-09-24
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {

}

package com.zmm.reggie.service.impl;

import com.zmm.reggie.entity.Category;
import com.zmm.reggie.mapper.CategoryMapper;
import com.zmm.reggie.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜品及套餐分类 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-09-24
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

}

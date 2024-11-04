package com.zmm.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zmm.reggie.entity.Category;
import com.zmm.reggie.service.ICategoryService;
import com.zmm.reggie.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 * 菜品及套餐分类 前端控制器
 * </p>
 *
 * @author zmm
 * @since 2024-09-24
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    /**
     * 查找所有分类列表
     * @return
     */
    @GetMapping("/findCategoryList")
    public ResultVo<List<Category>> findCategoryList() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        List<Category> categoryList = categoryService.list(queryWrapper);
        if (categoryList != null && categoryList.size() > 0) {
            return ResultVo.success(categoryList);
        } else {
            return ResultVo.fail("查询分类列表失败！");
        }
    }

    @PostMapping("/addCategory")
    public ResultVo addCategory(@RequestBody Category category) {
        String name = "";
        if (category.getType() == 1) {
            name = "菜品";
        } else {
            name = "套餐";
        }
        boolean result = categoryService.save(category);
        if (result) {
            return ResultVo.success("新增" + name + "分类成功！");
        } else {
            return ResultVo.fail("新增" + name + "分类失败！");
        }
    }

    /**
     * 根据分类名称查询分类信息
     * @param categoryName
     * @return
     */
    @GetMapping("/findCategoryByName")
    public ResultVo<Category> findCategoryByName(@RequestParam(value = "categoryName") String categoryName) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getName, categoryName);
        Category category = categoryService.getOne(queryWrapper);
        if (category != null) {
            return ResultVo.success(category);
        } else {
            return ResultVo.fail("根据分类名称查询分类信息失败！");
        }
    }

    /**
     * 根据分类ID更新分类信息
     * @param category
     * @return
     */
    @PutMapping("/updateCategoryById")
    public ResultVo updateCategoryById(@RequestBody Category category) {
        boolean result = categoryService.updateById(category);
        if (result) {
            return ResultVo.success("更新分类成功！");
        } else {
            return ResultVo.fail("更新分类失败！");
        }
    }

    @DeleteMapping("/delCategoryById/{id}")
    public ResultVo delCategoryById(@PathVariable("id") BigInteger id) {
        boolean result = categoryService.removeById(id);
        if (result) {
            return ResultVo.success();
        } else {
            return ResultVo.fail();
        }
    }
}

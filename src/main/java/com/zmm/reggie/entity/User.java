package com.zmm.reggie.entity;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author zmm
 * @since 2024-09-23
 */
@Getter
@Setter
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 性别
     */
    private String sex;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 状态 0:禁用，1:正常
     */
    private Integer status;


}

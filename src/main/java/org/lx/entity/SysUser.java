package org.lx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author lx
 * @since 2026-05-02 13:06
 */
@Getter
@Setter
@TableName("t_sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 加密密码
     */
    @TableField("password")
    private String password;

    /**
     * 真实姓名
     */
    @TableField("real_name")
    private String real_name;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 状态: 0禁用 1启用
     */
    @TableField("status")
    private Byte status;

    /**
     * 逻辑删除: 0未删除 1已删除
     */
    @TableField("is_deleted")
    private Byte is_deleted;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime create_time;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime update_time;
}

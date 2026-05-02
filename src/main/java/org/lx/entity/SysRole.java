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
 * 角色表
 * </p>
 *
 * @author lx
 * @since 2026-05-02 13:06
 */
@Getter
@Setter
@TableName("t_sys_role")
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String role_name;

    /**
     * 角色编码(如ADMIN, STORE_MGR)
     */
    @TableField("role_code")
    private String role_code;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 状态: 0禁用 1启用
     */
    @TableField("status")
    private Byte status;

    /**
     * 逻辑删除
     */
    @TableField("is_deleted")
    private Byte is_deleted;

    @TableField("create_time")
    private LocalDateTime create_time;

    @TableField("update_time")
    private LocalDateTime update_time;
}

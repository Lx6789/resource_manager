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
 * 权限菜单表
 * </p>
 *
 * @author lx
 * @since 2026-05-02 13:06
 */
@Getter
@Setter
@TableName("t_sys_menu")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父菜单ID, 0为顶级
     */
    @TableField("parent_id")
    private Long parent_id;

    /**
     * 菜单名称
     */
    @TableField("menu_name")
    private String menu_name;

    /**
     * 类型: 0目录 1菜单 2按钮
     */
    @TableField("menu_type")
    private Byte menu_type;

    /**
     * 前端路由或接口路径
     */
    @TableField("path")
    private String path;

    /**
     * 权限标识(如resource:upload)
     */
    @TableField("permission")
    private String permission;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    @TableField("status")
    private Byte status;

    @TableField("is_deleted")
    private Byte is_deleted;

    @TableField("create_time")
    private LocalDateTime create_time;

    @TableField("update_time")
    private LocalDateTime update_time;
}

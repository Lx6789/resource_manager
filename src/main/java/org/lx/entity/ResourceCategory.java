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
 * 资源分类表
 * </p>
 *
 * @author lx
 * @since 2026-05-02 13:06
 */
@Getter
@Setter
@TableName("t_resource_category")
public class ResourceCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 分类名称
     */
    @TableField("name")
    private String name;

    /**
     * 父分类ID, 0为根分类
     */
    @TableField("parent_id")
    private Long parent_id;

    /**
     * 层级
     */
    @TableField("level")
    private Byte level;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    @TableField("is_deleted")
    private Byte is_deleted;

    @TableField("create_time")
    private LocalDateTime create_time;

    @TableField("update_time")
    private LocalDateTime update_time;
}

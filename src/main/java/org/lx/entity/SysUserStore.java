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
 * 用户-门店绑定表（门店运营管理哪些门店）
 * </p>
 *
 * @author lx
 * @since 2026-05-04 14:04
 */
@Getter
@Setter
@TableName("t_sys_user_store")
public class SysUserStore implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long user_id;

    /**
     * 门店ID
     */
    @TableField("store_id")
    private Long store_id;

    @TableField("create_time")
    private LocalDateTime create_time;
}

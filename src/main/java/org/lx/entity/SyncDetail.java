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
 * 同步明细表(核心:记录每个文件到每个门店的同步状态)
 * </p>
 *
 * @author lx
 * @since 2026-05-02 13:06
 */
@Getter
@Setter
@TableName("t_sync_detail")
public class SyncDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 明细ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 同步任务ID
     */
    @TableField("task_id")
    private Long task_id;

    /**
     * 资源文件ID
     */
    @TableField("file_id")
    private Long file_id;

    /**
     * 门店ID
     */
    @TableField("store_id")
    private Long store_id;

    /**
     * 同步状态: 0待同步 1同步中 2已同步 -1失败
     */
    @TableField("sync_status")
    private Byte sync_status;

    /**
     * 下发到门店的独立副本key
     */
    @TableField("store_file_key")
    private String store_file_key;

    @TableField("create_time")
    private LocalDateTime create_time;

    /**
     * 实际同步完成时间
     */
    @TableField("sync_time")
    private LocalDateTime sync_time;
}

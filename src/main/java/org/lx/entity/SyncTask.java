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
 * 同步任务主表
 * </p>
 *
 * @author lx
 * @since 2026-05-02 13:06
 */
@Getter
@Setter
@TableName("t_sync_task")
public class SyncTask implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 同步任务ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 任务名称(如:春节海报投放)
     */
    @TableField("task_name")
    private String task_name;

    /**
     * 任务描述
     */
    @TableField("description")
    private String description;

    /**
     * 状态: 0草稿 1待审批 2已批准 3同步中 4已完成 -1已驳回
     */
    @TableField("status")
    private Byte status;

    /**
     * Flowable流程实例ID
     */
    @TableField("process_instance_id")
    private String process_instance_id;

    /**
     * 创建人ID
     */
    @TableField("create_by")
    private Long create_by;

    @TableField("create_time")
    private LocalDateTime create_time;

    /**
     * 审批时间
     */
    @TableField("approve_time")
    private LocalDateTime approve_time;

    @TableField("update_time")
    private LocalDateTime update_time;
}

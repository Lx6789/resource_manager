package org.lx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 异步任务记录表
 * </p>
 *
 * @author lx
 * @since 2026-05-02 13:06
 */
@Getter
@Setter
@TableName("t_task_record")
@Accessors(chain = true)
public class TaskRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 关联的资源文件ID
     */
    @TableField("file_id")
    private Long fileId;

    /**
     * 任务类型: WATERMARK/TRANSCODE/GENERATE_PREVIEW
     */
    @TableField("task_type")
    private String taskType;

    /**
     * 任务状态: 0待处理 1处理中 2成功 -1失败
     */
    @TableField("task_status")
    private int taskStatus;

    /**
     * 输入文件key
     */
    @TableField("input_key")
    private String inputKey;

    /**
     * 输出文件key
     */
    @TableField("output_key")
    private String outputKey;

    /**
     * 错误信息
     */
    @TableField("error_msg")
    private String errorMsg;

    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 完成时间
     */
    @TableField("finish_time")
    private LocalDateTime finishTime;
}

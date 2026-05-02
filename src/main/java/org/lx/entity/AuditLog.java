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
 * 操作审计日志表
 * </p>
 *
 * @author lx
 * @since 2026-05-02 13:06
 */
@Getter
@Setter
@TableName("t_audit_log")
public class AuditLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long user_id;

    @TableField("username")
    private String username;

    /**
     * 操作类型(如:资源上传/删除/审批)
     */
    @TableField("operation")
    private String operation;

    /**
     * 请求方法
     */
    @TableField("method")
    private String method;

    /**
     * 请求参数(JSON)
     */
    @TableField("params")
    private String params;

    /**
     * 操作IP
     */
    @TableField("ip")
    private String ip;

    /**
     * 操作结果: 0失败 1成功
     */
    @TableField("status")
    private Byte status;

    @TableField("create_time")
    private LocalDateTime create_time;
}

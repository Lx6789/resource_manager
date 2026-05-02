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
 * 门店表
 * </p>
 *
 * @author lx
 * @since 2026-05-02 13:06
 */
@Getter
@Setter
@TableName("t_store")
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 门店ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 门店名称
     */
    @TableField("store_name")
    private String store_name;

    /**
     * 门店编码(唯一)
     */
    @TableField("store_code")
    private String store_code;

    /**
     * 门店地址
     */
    @TableField("address")
    private String address;

    /**
     * 联系人
     */
    @TableField("contact_person")
    private String contact_person;

    /**
     * 联系电话
     */
    @TableField("contact_phone")
    private String contact_phone;

    /**
     * 状态: 0禁用 1营业中
     */
    @TableField("status")
    private Byte status;

    @TableField("is_deleted")
    private Byte is_deleted;

    @TableField("create_time")
    private LocalDateTime create_time;

    @TableField("update_time")
    private LocalDateTime update_time;
}

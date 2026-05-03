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
 * 资源文件主表
 * </p>
 *
 * @author lx
 * @since 2026-05-02 13:06
 */
@Getter
@Setter
@TableName("t_resource_file")
@Accessors(chain = true)
public class ResourceFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 资源ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 原始文件名
     */
    @TableField("file_name")
    private String fileName;

    /**
     * MinIO中的存储路径/key
     */
    @TableField("file_key")
    private String fileKey;

    /**
     * 文件MD5值(秒传核心)
     */
    @TableField("file_md5")
    private String fileMd5;

    /**
     * 文件大小(字节)
     */
    @TableField("file_size")
    private Long fileSize;

    /**
     * MIME类型,如image/png
     */
    @TableField("mime_type")
    private String mimeType;

    /**
     * 文件类型: 1图片 2视频 3音频 4文档 5其他
     */
    @TableField("file_type")
    private int fileType;

    /**
     * 预览文件key(视频缩略图/低清版)
     */
    @TableField("preview_key")
    private String previewKey;

    /**
     * 所属分类ID
     */
    @TableField("category_id")
    private Long categoryId;

    /**
     * 状态: 0删除 1正常 2转码中
     */
    @TableField("status")
    private int status;

    /**
     * 上传者ID
     */
    @TableField("create_by")
    private Long createBy;

    @TableField("is_deleted")
    private Byte isDeleted;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;
}

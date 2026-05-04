package org.lx.service;

import org.lx.config.RespBean;
import org.lx.entity.ResourceFile;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 资源文件主表 服务类
 * </p>
 *
 * @author lx
 * @since 2026-05-02 13:06
 */
public interface ResourceFileService extends IService<ResourceFile> {

    /**
     * 上传文件
     * @param file
     * @return
     */
    RespBean upload(MultipartFile file);

    /**
     * 获取预览URL
     * @param id
     * @return
     */
    RespBean preview(Long id);

    /**
     * 下载文件
     * @param id
     * @param response
     */
    void download(Long id, HttpServletResponse response) throws IOException;

    /**
     * 资源列表
     * @param page
     * @param size
     * @param categoryId
     * @param fileType
     * @param keyword
     * @return
     */
    RespBean list(Integer page, Integer size, Long categoryId, Integer fileType, String keyword);

    /**
     * 给资源打标签
     * @param fileId
     * @param tagIds
     * @return
     */
    RespBean setTags(Long fileId, List<Long> tagIds);
}

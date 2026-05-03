package org.lx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.lx.config.RespBean;
import org.lx.entity.ResourceFile;
import org.lx.mapper.ResourceFileMapper;
import org.lx.service.ResourceFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lx.utils.MinioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * <p>
 * 资源文件主表 服务实现类
 * </p>
 *
 * @author lx
 * @since 2026-05-02 13:06
 */

@Slf4j
@Service
public class ResourceFileServiceImpl extends ServiceImpl<ResourceFileMapper, ResourceFile> implements ResourceFileService {

    @Autowired
    private MinioUtil minioUtil;

    /**
     * 上传文件
     * @param file
     * @return
     */
    @Override
    public RespBean upload(MultipartFile file) {
        try {
            log.info("开始上传文件...");
            String fileKey = minioUtil.uploadFile(file);
            String md5 = cn.hutool.crypto.digest.DigestUtil.md5Hex(file.getInputStream());

            ResourceFile resourceFile = new ResourceFile()
                    .setFileName(file.getOriginalFilename())
                    .setFileKey(fileKey)
                    .setFileMd5(md5)
                    .setFileSize(file.getSize())
                    .setMimeType(file.getContentType())
                    .setFileType(1)
                    .setStatus(1);

            save(resourceFile);
            log.info("文件上传成功...");
            return RespBean.success(200, "上传成功", resourceFile);

        } catch (Exception e) {
            log.error(e.getMessage());
            return RespBean.error(500, "上传失败：" + e.getMessage());
        }
    }

    /**
     * 获取预览URL
     * @param id
     * @return
     */
    @Override
    public RespBean preview(Long id) {
        try {
            log.info("开始获取url...");
            ResourceFile file = getById(id);
            if (file == null) {
                return RespBean.error(404, "文件不存在");
            }
            String url = minioUtil.getPreviewUrl(file.getFileKey());
            log.info("获取url成功...");
            return RespBean.success(200, "预览链接", url);
        } catch (Exception e) {
            log.error(e.getMessage());
            return RespBean.error(500, "获取预览链接失败：" + e.getMessage());
        }
    }

    /**
     * 下载文件
     * @param id
     * @param response
     */
    @Override
    public void download(Long id, HttpServletResponse response) {
        try {
            log.info("开始下载文件...");
            ResourceFile file = getById(id);
            if (file == null) {
                response.setStatus(404);
                return;
            }
            InputStream inputStream = minioUtil.downloadFile(file.getFileKey());
            response.setContentType(file.getMimeType());
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + URLEncoder.encode(file.getFileName(), "UTF-8"));
            org.apache.tomcat.util.http.fileupload.IOUtils.copy(inputStream, response.getOutputStream());
            log.info("下载文件成功...");
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setStatus(500);
        }
    }

    /**
     * 资源列表
     * @param page
     * @param size
     * @param categoryId
     * @param fileType
     * @return
     */
    @Override
    public RespBean list(Integer page, Integer size, Long categoryId, Integer fileType) {
        try {
            log.info("开始获取资源列表...");
            LambdaQueryWrapper<ResourceFile> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ResourceFile::getStatus, 1);
            if (categoryId != null) {
                wrapper.eq(ResourceFile::getCategoryId, categoryId);
            }
            if (fileType != null) {
                wrapper.eq(ResourceFile::getFileType, fileType);
            }
            wrapper.orderByDesc(ResourceFile::getCreateTime);

            IPage<ResourceFile> result = page(new Page<>(page, size), wrapper);
            log.info("获取资源列表成功...");
            return RespBean.success(200, "查询成功", result);
        } catch (Exception e) {
            log.error(e.getMessage());
            return RespBean.error(500, "获取资源列表失败" + e.getMessage());
        }
    }
}

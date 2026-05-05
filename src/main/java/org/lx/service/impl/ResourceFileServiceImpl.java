package org.lx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.lx.config.RespBean;
import org.lx.entity.ResourceFile;
import org.lx.entity.ResourceFileTag;
import org.lx.mapper.ResourceFileMapper;
import org.lx.mapper.ResourceFileTagMapper;
import org.lx.service.ResourceFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lx.service.ResourceTagService;
import org.lx.service.TranscodeService;
import org.lx.service.WatermarkService;
import org.lx.utils.MinioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.lx.entity.ResourceTag;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private ResourceFileTagMapper resourceFileTagMapper;

    @Autowired
    private ResourceTagService resourceTagService;

    @Autowired
    private WatermarkService watermarkService;

    @Autowired
    private TranscodeService transcodeService;

    @Override
    public RespBean upload(MultipartFile file) {
        String md5 = minioUtil.calculateMd5(file);

        ResourceFile existFile = lambdaQuery().eq(ResourceFile::getFileMd5, md5).one();
        if (existFile != null) {
            log.info("秒传成功，复用已有文件：{}", existFile.getFileKey());
            return RespBean.success(200, "秒传成功", existFile);
        }

        String fileKey = minioUtil.uploadFile(file);

        // 判断文件类型，自动识别图片还是视频
        String contentType = file.getContentType();
        int fileType = 1;  // 默认图片
        if (contentType != null && contentType.startsWith("video/")) {
            fileType = 2;
        }

        ResourceFile resourceFile = new ResourceFile()
                .setFileName(file.getOriginalFilename())
                .setFileKey(fileKey)
                .setFileMd5(md5)
                .setFileSize(file.getSize())
                .setMimeType(contentType)
                .setFileType(fileType)   // ← 动态设置
                .setStatus(1);
        save(resourceFile);

        // 按文件类型触发异步任务
        if (fileType == 1) {
            watermarkService.processWatermark(resourceFile.getId(), resourceFile.getFileKey());
        } else if (fileType == 2) {
            transcodeService.processTranscode(resourceFile.getId(), resourceFile.getFileKey());
        }

        return RespBean.success(200, "上传成功", resourceFile);
    }

    @Override
    public RespBean preview(Long id) {
        ResourceFile file = lambdaQuery()
                .eq(ResourceFile::getId, id)
                .eq(ResourceFile::getStatus, 1)
                .one();
        if (file == null) {
            return RespBean.error(404, "文件不存在或已被删除");
        }
        String url = minioUtil.getPreviewUrl(file.getFileKey());
        return RespBean.success(200, "预览链接", url);
    }

    @Override
    public void download(Long id, HttpServletResponse response) throws IOException {
        ResourceFile file = lambdaQuery()
                .eq(ResourceFile::getId, id)
                .eq(ResourceFile::getStatus, 1)
                .one();
        if (file == null) {
            response.setStatus(404);
            return;
        }
        InputStream inputStream = minioUtil.downloadFile(file.getFileKey());
        response.setContentType(file.getMimeType());
        response.setHeader("Content-Disposition",
                "attachment; filename=" + URLEncoder.encode(file.getFileName(), "UTF-8"));
        org.apache.tomcat.util.http.fileupload.IOUtils.copy(inputStream, response.getOutputStream());
    }

    @Override
    public RespBean list(Integer page, Integer size, Long categoryId, Integer fileType, String keyword) {
        LambdaQueryWrapper<ResourceFile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ResourceFile::getStatus, 1);

        if (categoryId != null) {
            wrapper.eq(ResourceFile::getCategoryId, categoryId);
        }
        if (fileType != null) {
            wrapper.eq(ResourceFile::getFileType, fileType);
        }

        if (keyword != null && !keyword.trim().isEmpty()) {
            // 先按标签搜索
            List<Long> tagIds = resourceTagService.lambdaQuery()
                    .like(ResourceTag::getTagName, keyword)
                    .list()
                    .stream()
                    .map(ResourceTag::getId)
                    .collect(Collectors.toList());

            if (!tagIds.isEmpty()) {
                List<Long> fileIds = resourceFileTagMapper.selectList(
                        new LambdaQueryWrapper<ResourceFileTag>()
                                .in(ResourceFileTag::getTagId, tagIds)
                ).stream().map(ResourceFileTag::getFileId).distinct().collect(Collectors.toList());

                if (!fileIds.isEmpty()) {
                    wrapper.in(ResourceFile::getId, fileIds);
                } else {
                    wrapper.eq(ResourceFile::getId, -1L);
                }
            } else {
                wrapper.like(ResourceFile::getFileName, keyword);
            }
        }

        wrapper.orderByDesc(ResourceFile::getCreateTime);
        IPage<ResourceFile> result = this.baseMapper.selectPage(new Page<>(page, size), wrapper);
        return RespBean.success(200, "查询成功", result);
    }

    @Override
    public RespBean setTags(Long fileId, List<Long> tagIds) {
        ResourceFile file = lambdaQuery()
                .eq(ResourceFile::getId, fileId)
                .eq(ResourceFile::getStatus, 1)
                .one();
        if (file == null) {
            return RespBean.error(404, "文件不存在或已被删除");
        }

        resourceFileTagMapper.delete(
                new LambdaQueryWrapper<ResourceFileTag>().eq(ResourceFileTag::getFileId, fileId));

        if (tagIds != null && !tagIds.isEmpty()) {
            for (Long tagId : tagIds) {
                ResourceFileTag fileTag = new ResourceFileTag()
                        .setFileId(fileId)
                        .setTagId(tagId);
                resourceFileTagMapper.insert(fileTag);
            }
        }

        return RespBean.success(200, "标签设置成功", null);
    }
}

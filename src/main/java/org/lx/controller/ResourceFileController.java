package org.lx.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.lx.config.RespBean;
import org.lx.service.ResourceFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;

/**
* @Title: ResourceFileController
* @Author: MrLu2
* @Package: org.lx.controller
* @Date: 2026/5/2 13:23
* @Description: 文件上传/下载/预览、秒传、标签管理
*/

@Slf4j
@RestController
@RequestMapping("/api/resource")
@Tag(name = "文件管理")
public class ResourceFileController {

    /**
     *
     * 5. ResourceFileController（资源管理——核心模块）
     * 接口	方法	路径	说明
     * 上传文件	POST	/api/resource/upload	支持分片上传，返回 fileId + URL
     * 秒传校验	POST	/api/resource/check-md5	传 MD5，查是否已存在
     * 下载文件	GET	/api/resource/download/{id}	从 MinIO 下载文件
     * 预览文件	GET	/api/resource/preview/{id}	返回预览 URL（缩略图/低清版）
     * 分页查询资源	GET	/api/resource/list	支持按分类、标签、文件类型筛选
     * 查询资源详情	GET	/api/resource/{id}	文件元信息
     * 删除资源	DELETE	/api/resource/{id}	逻辑删除
     * 批量打标签	POST	/api/resource/{id}/tags	给资源添加标签
     * 查询转码进度	GET	/api/resource/{id}/task	查看异步任务状态
     *
     */

    @Autowired
    private ResourceFileService resourceFileService;

    @PostMapping(value = "/upload", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "上传文件")
    public RespBean upload(@Parameter(description = "文件", required = true)
                           @RequestParam("file") MultipartFile file) {
        return resourceFileService.upload(file);
    }

    @GetMapping("/preview/{id}")
    @Operation(summary = "获取预览URL")
    public RespBean preview(@PathVariable Long id) {
        return resourceFileService.preview(id);
    }

    @GetMapping("/download/{id}")
    @Operation(summary = "下载文件")
    public void download(@PathVariable Long id, HttpServletResponse response) {
        resourceFileService.download(id, response);
    }

    @GetMapping("/list")
    @Operation(summary = "资源列表")
    public RespBean list(@RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "10") Integer size,
                         @RequestParam(required = false) Long categoryId,
                         @RequestParam(required = false) Integer fileType) {
        return resourceFileService.list(page, size, categoryId, fileType);
    }

}

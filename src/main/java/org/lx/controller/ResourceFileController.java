package org.lx.controller;

/**
* @Title: ResourceFileController
* @Author: MrLu2
* @Package: org.lx.controller
* @Date: 2026/5/2 13:23
* @Description: 文件上传/下载/预览、秒传、标签管理
*/
    
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

}

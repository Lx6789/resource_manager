package org.lx.utils;

import cn.hutool.core.util.IdUtil;
import io.minio.*;
import io.minio.http.Method;
import org.lx.config.MinioProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;
/**
 * @Title: MinioUtil
 * @Author: MrLu2
 * @Package: org.lx.utils
 * @Date: 2026/5/3 15:03
 * @Description: mino工具类
 */

@Component
public class MinioUtil {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioProperties minioProperties;

    /**
     * 上传文件
     */
    public String uploadFile(MultipartFile file) {
        try {
            String bucketName = minioProperties.getBucketName();
            boolean bucketExists = minioClient.bucketExists(
                    BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            String originalName = file.getOriginalFilename();
            String suffix = originalName.substring(originalName.lastIndexOf("."));
            String objectKey = IdUtil.simpleUUID() + suffix;

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectKey)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());

            return objectKey;
        } catch (Exception e) {
            throw new RuntimeException("文件上传失败：" + e.getMessage(), e);
        }
    }

    /**
     * 获取文件预览 URL（有效期 7 天）
     */
    public String getPreviewUrl(String objectKey) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(minioProperties.getBucketName())
                            .object(objectKey)
                            .expiry(7, TimeUnit.DAYS)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException("获取预览链接失败：" + e.getMessage(), e);
        }
    }

    /**
     * 下载文件（返回文件流）
     */
    public InputStream downloadFile(String objectKey) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .object(objectKey)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException("文件下载失败：" + e.getMessage(), e);
        }
    }

    /**
     * 计算文件的 MD5 值
     */
    public String calculateMd5(MultipartFile file) {
        try {
            return cn.hutool.crypto.digest.DigestUtil.md5Hex(file.getInputStream());
        } catch (Exception e) {
            throw new RuntimeException("计算MD5失败：" + e.getMessage(), e);
        }
    }
}
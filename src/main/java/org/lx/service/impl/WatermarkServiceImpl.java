package org.lx.service.impl;

import cn.hutool.core.util.IdUtil;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.lx.config.MinioProperties;
import org.lx.entity.TaskRecord;
import org.lx.service.TaskRecordService;
import org.lx.service.WatermarkService;
import org.lx.utils.WatermarkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @Title: WatermarkServiceImpl
 * @Author: MrLu2
 * @Package: org.lx.service.impl
 * @Date: 2026/5/4 14:58
 * @Description: 水印实现类
 */

@Slf4j
@Service
public class WatermarkServiceImpl implements WatermarkService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioProperties minioProperties;

    @Autowired
    private WatermarkUtil watermarkUtil;

    @Autowired
    private TaskRecordService taskRecordService;

    /**
     * 处理水印
     * @param fileId
     * @param inputKey
     */
    @Async("taskExecutor")
    @Override
    public void processWatermark(Long fileId, String inputKey) {
        // 1. 创建任务记录
        TaskRecord task = taskRecordService.createTask(fileId, "WATERMARK");

        try {
            // 2. 更新状态为处理中
            taskRecordService.updateTaskStatus(task.getId(), 1, null);

            // 3. 从 MinIO 下载原图
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            watermarkUtil.addTextWatermark(
                    minioClient.getObject(GetObjectArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .object(inputKey)
                            .build()),
                    outputStream,
                    "亦鸦亦鸦哟"
            );

            // 4. 上传带水印的图片
            String outputKey = "watermark/" + IdUtil.simpleUUID() + ".jpg";
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .object(outputKey)
                    .stream(new ByteArrayInputStream(outputStream.toByteArray()), outputStream.size(), -1)
                    .contentType("image/jpeg")
                    .build());

            // 5. 更新任务状态为成功
            taskRecordService.updateTaskStatus(task.getId(), 2, outputKey);
            log.info("水印处理完成，fileId: {}, outputKey: {}", fileId, outputKey);

        } catch (Exception e) {
            log.error("水印处理失败，fileId: {}", fileId, e);
            taskRecordService.updateTaskStatus(task.getId(), -1, null);
        }
    }
}

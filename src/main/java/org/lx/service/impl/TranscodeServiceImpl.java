package org.lx.service.impl;

import cn.hutool.core.util.IdUtil;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.lx.config.MinioProperties;
import org.lx.entity.ResourceFile;
import org.lx.entity.TaskRecord;
import org.lx.mapper.ResourceFileMapper;
import org.lx.service.TaskRecordService;
import org.lx.service.TranscodeService;
import org.lx.utils.VideoTranscodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;

/**
 * @Title: TranscodeServiceImpl
 * @Author: MrLu2
 * @Package: org.lx.service.impl
 * @Date: 2026/5/5 19:06
 * @Description: 转码实现类
 */

@Slf4j
@Service
public class TranscodeServiceImpl implements TranscodeService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioProperties minioProperties;

    @Autowired
    private VideoTranscodeUtil videoTranscodeUtil;

    @Autowired
    private TaskRecordService taskRecordService;

    @Autowired
    private ResourceFileMapper resourceFileMapper;

    @Async("asyncTaskExecutor")
    @Override
    public void processTranscode(Long fileId, String inputKey) {
        TaskRecord task = taskRecordService.createTask(fileId, "TRANSCODE");

        try {
            taskRecordService.updateTaskStatus(task.getId(), 1, null);

            // 1. 转码
            ByteArrayOutputStream transcodeOutput = new ByteArrayOutputStream();
            videoTranscodeUtil.transcodeToMp4(
                    minioClient.getObject(GetObjectArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .object(inputKey)
                            .build()),
                    transcodeOutput
            );

            String transcodeKey = "video/" + IdUtil.simpleUUID() + ".mp4";
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .object(transcodeKey)
                    .stream(new ByteArrayInputStream(transcodeOutput.toByteArray()),
                            transcodeOutput.size(), -1)
                    .contentType("video/mp4")
                    .build());

            // 2. 截取缩略图
            ByteArrayOutputStream thumbOutput = new ByteArrayOutputStream();
            videoTranscodeUtil.captureThumbnail(
                    new ByteArrayInputStream(transcodeOutput.toByteArray()),
                    thumbOutput
            );

            String thumbKey = "thumb/" + IdUtil.simpleUUID() + ".jpg";
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .object(thumbKey)
                    .stream(new ByteArrayInputStream(thumbOutput.toByteArray()),
                            thumbOutput.size(), -1)
                    .contentType("image/jpeg")
                    .build());

            // 3. 把转码后的视频写入 t_resource_file
            ResourceFile transcodeFile = new ResourceFile()
                    .setFileName("transcode_" + fileId + ".mp4")
                    .setFileKey(transcodeKey)
                    .setFileMd5("")
                    .setFileSize((long) transcodeOutput.size())
                    .setMimeType("video/mp4")
                    .setFileType(2)
                    .setStatus(1)
                    .setCreateTime(LocalDateTime.now());
            resourceFileMapper.insert(transcodeFile);

            // 4. 更新原视频的预览图
            ResourceFile originalFile = resourceFileMapper.selectById(fileId);
            if (originalFile != null) {
                originalFile.setPreviewKey(thumbKey);
                resourceFileMapper.updateById(originalFile);
            }

            taskRecordService.updateTaskStatus(task.getId(), 2, transcodeKey);
            log.info("转码完成，fileId: {}, transcodeKey: {}", fileId, transcodeKey);

        } catch (Exception e) {
            log.error("转码失败，fileId: {}", fileId, e);
            taskRecordService.updateTaskStatus(task.getId(), -1, null);
        }
    }
}

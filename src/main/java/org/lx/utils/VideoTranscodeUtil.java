package org.lx.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

/**
 * @Title: VideoTranscodeUtil
 * @Author: MrLu2
 * @Package: org.lx.utils
 * @Date: 2026/5/5 19:01
 * @Description: 视频转码工具类
 */

@Slf4j
@Component
public class VideoTranscodeUtil {

    /**
     * 视频转码为 MP4
     * @param inputStream  原视频流
     * @param outputStream 转码后输出流
     */
    public void transcodeToMp4(InputStream inputStream, OutputStream outputStream) throws Exception {
        // 写入项目目录而非系统Temp目录
        String tmpDir = System.getProperty("user.dir");
        File tempInput = new File(tmpDir + "/temp_transcode_input.tmp");
        File tempOutput = new File(tmpDir + "/temp_transcode_output.mp4");
        Files.copy(inputStream, tempInput.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        ProcessBuilder pb = new ProcessBuilder(
                "ffmpeg", "-i", tempInput.getAbsolutePath(),
                "-c:v", "libx264", "-c:a", "aac",
                "-preset", "fast", "-y",
                tempOutput.getAbsolutePath()
        );
        pb.redirectErrorStream(true);
        Process process = pb.start();
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            try (InputStream errorStream = process.getInputStream()) {
                String errorMsg = new String(errorStream.readAllBytes());
                tempInput.delete();
                tempOutput.delete();
                throw new RuntimeException("视频转码失败，exit code: " + exitCode + ", 详情: " + errorMsg);
            }
        }

        Files.copy(tempOutput.toPath(), outputStream);
        tempInput.delete();
        tempOutput.delete();
    }

    /**
     * 截取视频第一帧作为缩略图
     * @param inputStream  视频流
     * @param outputStream 缩略图输出流
     */
    public void captureThumbnail(InputStream inputStream, OutputStream outputStream) throws Exception {
        String tmpDir = System.getProperty("user.dir");
        File tempInput = new File(tmpDir + "/temp_thumb_input.tmp");
        File tempOutput = new File(tmpDir + "/temp_thumb_output.jpg");
        Files.copy(inputStream, tempInput.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        ProcessBuilder pb = new ProcessBuilder(
                "ffmpeg", "-i", tempInput.getAbsolutePath(),
                "-ss", "00:00:01",
                "-vframes", "1",
                "-y",
                tempOutput.getAbsolutePath()
        );
        pb.redirectErrorStream(true);
        Process process = pb.start();
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            try (InputStream errorStream = process.getInputStream()) {
                String errorMsg = new String(errorStream.readAllBytes());
                tempInput.delete();
                tempOutput.delete();
                throw new RuntimeException("缩略图截取失败，exit code: " + exitCode + ", 详情: " + errorMsg);
            }
        }

        Files.copy(tempOutput.toPath(), outputStream);
        tempInput.delete();
        tempOutput.delete();
    }
}

package org.lx.service;

/**
 * @Title: WatermarkService
 * @Author: MrLu2
 * @Package: org.lx.service
 * @Date: 2026/5/4 14:57
 * @Description: 水印接口
 */

public interface WatermarkService {

    /**
     * 处理水印
     * @param fileId
     * @param inputKey
     */
    void processWatermark(Long fileId, String inputKey);
}

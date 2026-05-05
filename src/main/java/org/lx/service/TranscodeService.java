package org.lx.service;

/**
 * @Title: TranscodeService
 * @Author: MrLu2
 * @Package: org.lx.service
 * @Date: 2026/5/5 19:05
 * @Description: 转码及相关接口
 */

public interface TranscodeService {

    void processTranscode(Long fileId, String inputKey);
}

package org.lx.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Title: GlobalExceptionHandler
 * @Author: MrLu2
 * @Package: org.lx.config
 * @Date: 2026/5/4 14:09
 * @Description: 全局异常处理
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public RespBean handleRuntimeException(RuntimeException e) {
        log.error("运行时异常：{}", e.getMessage());
        return RespBean.error(500, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public RespBean handleException(Exception e) {
        log.error("系统异常：{}", e.getMessage(), e);
        return RespBean.error(500, "服务器内部错误：" + e.getMessage());
    }
}

package org.lx;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Title: APP
 * @Author: MrLu2
 * @Package: org.lx
 * @Date: 2026/5/2 13:07
 * @Description: 启动类
 */

@Slf4j
@SpringBootApplication
@MapperScan("org.lx.mapper")
public class APP {

    public static void main(String[] args) {
        SpringApplication.run(APP.class, args);
        log.info("项目启动成功...");
    }
}

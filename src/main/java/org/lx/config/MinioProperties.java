package org.lx.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Title: MinioPropertiesConfig
 * @Author: MrLu2
 * @Package: org.lx.config
 * @Date: 2026/5/3 14:59
 * @Description: mino属性
 */

@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;
}

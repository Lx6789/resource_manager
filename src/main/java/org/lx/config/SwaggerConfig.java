package org.lx.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: SwaggerConfig
 * @Author: MrLu2
 * @Package: org.lx.config
 * @Date: 2026/5/2 17:07
 * @Description: swagger-ui配置类
 */

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // 安全方案名称，可以随便取
        String securitySchemeName = "Authorization";

        return new OpenAPI()
                .info(new Info()
                        .title("企业级媒体资源管理平台")
                        .version("1.0")
                        .description("接口文档"))
                // 添加全局 Token 输入框
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}

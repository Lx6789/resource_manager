import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @Title: CodeGenerator
 * @Author: MrLu2
 * @Package: PACKAGE_NAME
 * @Date: 2026/5/2 13:04
 * @Description: 代码生成器
 */

public class CodeGenerator {

    public static void main(String[] args) {
        FastAutoGenerator.create(
                        "jdbc:mysql://localhost:3306/resource_manager?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai",
                        "root",
                        "123456"
                )
                .globalConfig(builder -> builder
                        .author("lx")
                        .outputDir(System.getProperty("user.dir") + "/src/main/java")
                        .commentDate("yyyy-MM-dd HH:mm")
                        .disableOpenDir()
                )
                .packageConfig(builder -> builder
                        .parent("org.lx")
                        .entity("entity")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .controller("controller")
                        .xml("mapper")
                        .pathInfo(Collections.singletonMap(
                                OutputFile.xml,
                                System.getProperty("user.dir") + "/src/main/resources/mapper"
                        ))
                )
                .strategyConfig(builder -> builder
                        .addInclude(
                                // ========== 用户与权限模块 (5张) ==========
                                "t_sys_user",
                                "t_sys_role",
                                "t_sys_menu",
                                "t_sys_user_role",
                                "t_sys_role_menu",

                                // ========== 资源管理模块 (4张) ==========
                                "t_resource_category",
                                "t_resource_file",
                                "t_resource_tag",
                                "t_resource_file_tag",

                                // ========== 异步任务模块 (1张) ==========
                                "t_task_record",

                                // ========== 门店与分发模块 (6张) ==========
                                "t_store",
                                "t_sync_task",
                                "t_sync_task_resource",
                                "t_sync_task_store",
                                "t_sync_detail",

                                // ========== 审计日志模块 (1张) ==========
                                "t_audit_log",

                                // ========== 用户-门店绑定 (1张) ==========
                                "t_sys_user_store"
                        )
                        .addTablePrefix("t_")

                        // Entity 策略
                        .entityBuilder()
                        .enableLombok()
                        .enableTableFieldAnnotation()
                        .naming(com.baomidou.mybatisplus.generator.config.rules.NamingStrategy.underline_to_camel)
                        .columnNaming(com.baomidou.mybatisplus.generator.config.rules.NamingStrategy.no_change)
                        .enableFileOverride()

                        // Controller 策略
                        .controllerBuilder()
                        .enableRestStyle()
                        .enableHyphenStyle()
                        .formatFileName("%sController")

                        // Service 策略
                        .serviceBuilder()
                        .formatServiceFileName("%sService")
                        .formatServiceImplFileName("%sServiceImpl")

                        // Mapper 策略
                        .mapperBuilder()
                        .enableBaseResultMap()
                        .enableBaseColumnList()
                        .formatMapperFileName("%sMapper")
                        .formatXmlFileName("%sMapper")
                )
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}

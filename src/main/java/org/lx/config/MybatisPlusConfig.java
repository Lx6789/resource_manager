package org.lx.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: MybatisPlusConfig
 * @Author: MrLu2
 * @Package: org.lx.config
 * @Date: 2026/5/4 14:07
 * @Description: mybatis配置类
 */

@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页插件
        //MyBatis-Plus 的分页功能依赖这个拦截器。没配的话，Page 对象不会自动拼接 LIMIT 语句，分页查询会返回全部数据。
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}

package org.lx.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)  // 开启 @PreAuthorize 注解
public class SecurityConfig {

    @Autowired
    private JwtAuthencationTokenFilter jwtAuthencationTokenFilter;

    @Autowired
    private RestAuthorizationEntryPoint restAuthorizationEntryPoint;

    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 放行 Swagger 和登录/退出接口
                .antMatchers(
                        "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**",
                        "/api/auth/login", "/api/auth/logout", "/api/auth/register"
                ).permitAll()
                // 其余接口全部需要认证
                .anyRequest().authenticated()
                .and()
                .headers().cacheControl();

        // 添加 JWT 过滤器
        http.addFilterBefore(jwtAuthencationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // 未登录 / 权限不足 的自定义返回
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthorizationEntryPoint);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers(
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/webjars/**",
                "/css/**",
                "/js/**",
                "/index.html",
                "/favicon.ico",
                "/swagger-config/**"
        );
    }
}
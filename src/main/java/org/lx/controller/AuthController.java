package org.lx.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.lx.config.RespBean;
import org.lx.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: AuthController
 * @Author: MrLu2
 * @Package: org.lx.controller
 * @Date: 2026/5/2 13:21
 * @Description: 登录、退出、Token 刷新
 */


@Slf4j
@RestController
@RequestMapping("/api/auth")
@Tag(name = "用户相关接口")
public class AuthController {

    /**
     *
     * 接口	方法	路径	说明
     * 登录	POST	/api/auth/login	用户名+密码，返回 JWT
     * 退出	POST	/api/auth/logout	Token 加入 Redis 黑名单
     * 获取当前用户信息	GET	/api/auth/info	返回当前登录用户的角色、权限
     *
     */


    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/login")
    @Operation(summary = "用户名+密码，返回 JWT")
    public RespBean login(@RequestParam("username") String username,
                          @RequestParam("password") String password) {
        log.info("username: {}, password: {}", username, password);
        return sysUserService.login(username, password);
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public RespBean register(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("realName") String realName,
                             @RequestParam("phone") String phone) {
        log.info("username: {}, password: {}, realName: {}, phone: {}", username, password, realName, phone);
        return sysUserService.register(username, password, realName, phone);
    }
}

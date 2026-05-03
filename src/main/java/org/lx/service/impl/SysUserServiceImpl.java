package org.lx.service.impl;

import org.lx.config.RespBean;
import org.lx.utils.JwtTokenUtils;
import org.lx.entity.SysUser;
import org.lx.mapper.SysUserMapper;
import org.lx.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author lx
 * @since 2026-05-02 13:06
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Value("${jwt.token-prefix}")
    private String tokenHead;

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public RespBean login(String username, String password) {
        //1. 登陆验证
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            return RespBean.error(401, "用户名或密码错误");
        }
        if (!userDetails.isEnabled()) {
            return RespBean.error(401, "账号已被禁用，清联系管理员");
        }

        //2. 更新security登录用户对象
        UsernamePasswordAuthenticationToken authentication
                = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //3. 生成token
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userDetails.getUsername());
        claims.put("created", System.currentTimeMillis());
        String token = jwtTokenUtils.generateToken(claims);

        //4. 返回
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return RespBean.success(200, "登陆成功", tokenMap);
    }

    /**
     * 用户注册
     * @param username
     * @param password
     * @param realName
     * @param phone
     * @return
     */
    @Override
    public RespBean register(String username, String password, String realName, String phone) {
        // 1. 检查手机号是否已被注册（手机号作为企业内唯一标识）
        SysUser existByPhone = lambdaQuery().eq(SysUser::getPhone, phone).one();
        if (existByPhone != null) {
            return RespBean.error(400, "该手机号已绑定账号，一个员工只能有一个账号");
        }

        // 2. 检查用户名是否存在
        SysUser existByUsername = lambdaQuery().eq(SysUser::getUsername, username).one();
        if (existByUsername != null) {
            return RespBean.error(400, "用户名已被占用");
        }

        // 3. 创建用户
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username)
                .setPassword(passwordEncoder.encode(password))
                .setReal_name(realName)
                .setPhone(phone)
                .setStatus(1);
        save(sysUser);

        return RespBean.success(200, "注册成功");
    }

    @Override
    public RespBean info() {
        // 1. 从 SecurityContext 获取当前登录用户
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return RespBean.error(401, "未登录");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 2. 前端只需要角色（权限），直接从 authorities 里取
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // 3. 组装返回数据
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("username", userDetails.getUsername());
        userInfo.put("roles", roles);

        return RespBean.success(200, "获取用户信息成功", userInfo);
    }
}

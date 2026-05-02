package org.lx.config.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.lx.entity.SysUser;
import org.lx.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserMapper.selectOne(
                new LambdaQueryWrapper<SysUser>()
                        .eq(SysUser::getUsername, username)
        );
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        return new User(
                sysUser.getUsername(),
                sysUser.getPassword(),
                sysUser.getStatus() == 1,    // 启用状态
                true, true, true,
                Collections.emptyList()       // 第一周先不给角色，第三周再加
        );
    }
}
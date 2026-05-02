package org.lx.service;

import org.lx.config.RespBean;
import org.lx.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author lx
 * @since 2026-05-02 13:06
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    RespBean login(String username, String password);

    /**
     * 用户注册
     * @param username
     * @param password
     * @param realName
     * @return
     */
    RespBean register(String username, String password, String realName, String phone);
}

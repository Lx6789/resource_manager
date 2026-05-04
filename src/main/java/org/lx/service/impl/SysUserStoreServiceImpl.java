package org.lx.service.impl;

import org.lx.entity.SysUserStore;
import org.lx.mapper.SysUserStoreMapper;
import org.lx.service.SysUserStoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户-门店绑定表（门店运营管理哪些门店） 服务实现类
 * </p>
 *
 * @author lx
 * @since 2026-05-04 14:04
 */
@Service
public class SysUserStoreServiceImpl extends ServiceImpl<SysUserStoreMapper, SysUserStore> implements SysUserStoreService {

}

package org.lx.service.impl;

import org.lx.entity.SyncDetail;
import org.lx.mapper.SyncDetailMapper;
import org.lx.service.SyncDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 同步明细表(核心:记录每个文件到每个门店的同步状态) 服务实现类
 * </p>
 *
 * @author lx
 * @since 2026-05-02 13:06
 */
@Service
public class SyncDetailServiceImpl extends ServiceImpl<SyncDetailMapper, SyncDetail> implements SyncDetailService {

}

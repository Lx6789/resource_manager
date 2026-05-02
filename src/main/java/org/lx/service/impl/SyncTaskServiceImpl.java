package org.lx.service.impl;

import org.lx.entity.SyncTask;
import org.lx.mapper.SyncTaskMapper;
import org.lx.service.SyncTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 同步任务主表 服务实现类
 * </p>
 *
 * @author lx
 * @since 2026-05-02 13:06
 */
@Service
public class SyncTaskServiceImpl extends ServiceImpl<SyncTaskMapper, SyncTask> implements SyncTaskService {

}

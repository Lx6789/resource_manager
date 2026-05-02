package org.lx.service.impl;

import org.lx.entity.TaskRecord;
import org.lx.mapper.TaskRecordMapper;
import org.lx.service.TaskRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 异步任务记录表 服务实现类
 * </p>
 *
 * @author lx
 * @since 2026-05-02 13:06
 */
@Service
public class TaskRecordServiceImpl extends ServiceImpl<TaskRecordMapper, TaskRecord> implements TaskRecordService {

}

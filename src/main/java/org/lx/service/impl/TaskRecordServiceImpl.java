package org.lx.service.impl;

import org.lx.entity.TaskRecord;
import org.lx.mapper.TaskRecordMapper;
import org.lx.service.TaskRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    /**
     * 创建任务
     * @param fileId
     * @param taskType
     * @return
     */
    @Override
    public TaskRecord createTask(Long fileId, String taskType) {
        TaskRecord task = new TaskRecord()
                .setFileId(fileId)
                .setTaskType(taskType)
                .setTaskStatus(0)      // 0=待处理
                .setInputKey(null);
        save(task);
        return task;
    }

    /**
     * 更新任务状态
     * @param taskId
     * @param status
     * @param outputKey
     */
    @Override
    public void updateTaskStatus(Long taskId, Integer status, String outputKey) {
        TaskRecord task = new TaskRecord()
                .setId(taskId)
                .setTaskStatus(status)
                .setOutputKey(outputKey)
                .setFinishTime(status == 2 || status == -1 ? LocalDateTime.now() : null);
        updateById(task);
    }
}

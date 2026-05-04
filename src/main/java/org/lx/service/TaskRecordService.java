package org.lx.service;

import org.lx.entity.TaskRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 异步任务记录表 服务类
 * </p>
 *
 * @author lx
 * @since 2026-05-02 13:06
 */
public interface TaskRecordService extends IService<TaskRecord> {

    /**
     * 创建任务记录
     * @param fileId
     * @param taskType
     * @return
     */
    TaskRecord createTask(Long fileId, String taskType);

    /**
     * 更新任务状态
     * @param taskId
     * @param status
     * @param outputKey
     */
    void updateTaskStatus(Long taskId, Integer status, String outputKey);
}

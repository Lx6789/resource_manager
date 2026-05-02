package org.lx.service.impl;

import org.lx.entity.AuditLog;
import org.lx.mapper.AuditLogMapper;
import org.lx.service.AuditLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作审计日志表 服务实现类
 * </p>
 *
 * @author lx
 * @since 2026-05-02 13:06
 */
@Service
public class AuditLogServiceImpl extends ServiceImpl<AuditLogMapper, AuditLog> implements AuditLogService {

}

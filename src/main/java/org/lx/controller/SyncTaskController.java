package org.lx.controller;

/**
* @Title: SyncTaskController
* @Author: MrLu2
* @Package: org.lx.controller
* @Date: 2026/5/2 13:24
* @Description: 创建同步任务、提交审批、查看同步明细
*/
    
public class SyncTaskController {

    /**
     *
     * 8. SyncTaskController（同步分发——业务闭环核心）
     * 接口	方法	路径	说明
     * 创建同步任务	POST	/api/sync/task	选择资源+门店，生成分发任务
     * 提交审批	POST	/api/sync/task/{id}/submit	启动 Flowable 审批流程
     * 审批通过/驳回	POST	/api/sync/task/{id}/approve	审批操作
     * 查询任务列表	GET	/api/sync/task/list	支持按状态筛选
     * 查询任务详情	GET	/api/sync/task/{id}	任务信息+关联资源+门店
     * 查询同步明细	GET	/api/sync/detail/{taskId}	每个文件到每个门店的状态
     * 重试失败同步	POST	/api/sync/detail/{id}/retry	重新同步单条失败记录
     *
     */

}

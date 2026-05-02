package org.lx.controller;

/**
* @Title: SysUserController
* @Author: MrLu2
* @Package: org.lx.controller
* @Date: 2026/5/2 13:22
* @Description: 用户 CRUD、分配角色、禁用/启用
*/
    
public class SysUserController {

    /**
     *
     * 2. SysUserController（用户管理）
     * 接口	方法	路径	说明
     * 分页查询用户列表	GET	/api/user/list	支持按用户名/状态筛选
     * 根据ID查询用户	GET	/api/user/{id}	用户详情
     * 新增用户	POST	/api/user	管理员创建账号
     * 修改用户	PUT	/api/user/{id}	修改用户信息
     * 删除用户	DELETE	/api/user/{id}	逻辑删除
     * 分配角色	POST	/api/user/{id}/roles	给用户分配角色
     * 禁用/启用	PUT	/api/user/{id}/status	修改用户状态
     *
     */
}

package org.lx.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.lx.config.RespBean;
import org.lx.entity.ResourceCategory;
import org.lx.service.ResourceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
* @Title: ResourceCategoryController
* @Author: MrLu2
* @Package: org.lx.controller
* @Date: 2026/5/2 13:23
* @Description: 资源分类树 CRUD
*/

@Slf4j
@RestController
@RequestMapping("/api/category")
@Tag(name = "资源分类树 CRUD")
public class ResourceCategoryController {

    /**
     *
     * 6. ResourceCategoryController（分类管理）
     * 接口	方法	路径	说明
     * 分类树	GET	/api/category/tree	完整的树形分类
     * 新增分类	POST	/api/category	创建分类
     * 修改分类	PUT	/api/category/{id}	重命名等
     * 删除分类	DELETE	/api/category/{id}	无子级时可删
     *
     */

    @Autowired
    private ResourceCategoryService resourceCategoryService;

    @GetMapping("/tree")
    @Operation(summary = "分类树")
    public RespBean getTree() {
        List<ResourceCategory> tree = resourceCategoryService.getCategoryTree();
        return RespBean.success(200, "查询成功", tree);
    }

}

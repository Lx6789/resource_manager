package org.lx.service.impl;

import org.lx.entity.ResourceCategory;
import org.lx.mapper.ResourceCategoryMapper;
import org.lx.service.ResourceCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 资源分类表 服务实现类
 * </p>
 *
 * @author lx
 * @since 2026-05-02 13:06
 */
@Service
public class ResourceCategoryServiceImpl extends ServiceImpl<ResourceCategoryMapper, ResourceCategory> implements ResourceCategoryService {

    /**
     * 查询分类树
     * @return
     */
    @Override
    public List<ResourceCategory> getCategoryTree() {
        // 查出所有分类
        List<ResourceCategory> allCategories = lambdaQuery()
                .eq(ResourceCategory::getIsDeleted, 0)
                .orderByAsc(ResourceCategory::getSort)
                .list();

        // 筛选出一级分类（parent_id = 0），然后递归找子分类
        return allCategories.stream()
                .filter(c -> c.getParentId() == 0)
                .map(c -> buildChildren(c, allCategories))
                .collect(Collectors.toList());
    }

    /**
     * 递归构建子分类
     * @param parent
     * @param allCategories
     * @return
     */
    private ResourceCategory buildChildren(ResourceCategory parent, List<ResourceCategory> allCategories) {
        List<ResourceCategory> children = allCategories.stream()
                .filter(c -> c.getParentId().equals(parent.getId()))
                .map(c -> buildChildren(c, allCategories))
                .collect(Collectors.toList());
        parent.setChildren(children);
        return parent;
    }
}

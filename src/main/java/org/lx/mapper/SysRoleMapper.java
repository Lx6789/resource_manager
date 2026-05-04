package org.lx.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.lx.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author lx
 * @since 2026-05-02 13:06
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据 userid 查询角色码
     * @param userId
     * @return
     */
    @Select("SELECT r.role_code FROM t_sys_role r " +
            "INNER JOIN t_sys_user_role ur ON r.id = ur.role_id " +
            "WHERE ur.user_id = #{userId} AND r.status = 1")
    List<String> selectRoleCodesByUserId(@Param("userId") Long userId);
}
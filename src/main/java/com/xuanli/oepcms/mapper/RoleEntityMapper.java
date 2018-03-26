package com.xuanli.oepcms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.RoleEntity;
@Mapper
public interface RoleEntityMapper {
	/**
	 * @Description:  TODO 删除方法 只能按照id删除
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月15日 下午2:08:38
	 */
    int deleteRoleEntity(Long roleId);
    /**
     * @Description:  TODO 增加方法
     * @CreateName:  QiaoYu 
     * @CreateDate:  2018年1月15日 下午2:08:48
     */
    int insertRoleEntity(RoleEntity record);
    /**
     * @Description:  TODO 按照id查询
     * @CreateName:  QiaoYu 
     * @CreateDate:  2018年1月15日 下午2:09:18
     */
    RoleEntity selectById(Long roleId);
    /**
     * @Description:  TODO 按照id 更新
     * @CreateName:  QiaoYu 
     * @CreateDate:  2018年1月15日 下午2:09:28
     */
    int updateRoleEntity(RoleEntity record);

}
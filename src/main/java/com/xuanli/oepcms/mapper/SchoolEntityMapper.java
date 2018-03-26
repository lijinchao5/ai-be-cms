package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.ClasEntity;
import com.xuanli.oepcms.entity.SchoolEntity;
import com.xuanli.oepcms.plugins.Page;
@Mapper
public interface SchoolEntityMapper {
    /**
	 * @Description:  TODO 删除方法 只能按照id删除
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月15日 下午2:08:38
	 */
    int deleteSchoolEntity(Long id);
    /**
     * @Description:  TODO 增加方法
     * @CreateName:  QiaoYu 
     * @CreateDate:  2018年1月15日 下午2:08:48
     */
    int insertSchoolEntity(SchoolEntity record);
    /**
     * @Description:  TODO 按照id查询
     * @CreateName:  QiaoYu 
     * @CreateDate:  2018年1月15日 下午2:09:18
     */
    SchoolEntity selectById(Long id);
    /**
     * @Description:  TODO 按照id 更新
     * @CreateName:  QiaoYu 
     * @CreateDate:  2018年1月15日 下午2:09:28
     */
    int updateSchoolEntity(SchoolEntity record);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月15日 下午5:16:05
	 */
	List<SchoolEntity> selectSchoolEntity(SchoolEntity schoolEntity);
	
	/*
	 * 查询最大id
	 */
	Integer selectMaxId();
	/*
	 * 查询分页总数
	 * 
	 */
	int findByPageTotal(SchoolEntity schoolEntity);
	
	/*
	 * 
	 * 查询分页
	 */
	List<SchoolEntity> findByPage(Page<SchoolEntity> pages,SchoolEntity schoolEntity);
	
	
}
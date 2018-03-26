package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.SectionEntity;
import com.xuanli.oepcms.plugins.Page;
@Mapper
public interface SectionEntityMapper {
	/**删除方法，根据id删除*/
    int deleteSectionEntity(Long id);

    /**增加方法*/
    int insertSectionEntity(SectionEntity record);
    
    /**查询方法,根据id查询*/
    SectionEntity selectById(Long id);

    /**更新方法*/
    int updateSectionEntity(SectionEntity record);

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月18日 上午9:55:16
	 */
	List<SectionEntity> getSectionEntity(SectionEntity sectionEntity);
	
	/**
	 * 
	 * @Description: 查询列表
	 * 
	 * @return	List<SectionEntity> 
	 * @author  WangMeng
	 */
	List<SectionEntity> getSectionEntityByPage(Page<SectionEntity> page,SectionEntity entity);
	
}
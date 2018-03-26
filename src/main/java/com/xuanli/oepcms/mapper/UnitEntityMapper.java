package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.BookEntity;
import com.xuanli.oepcms.entity.UnitEntity;
import com.xuanli.oepcms.plugins.Page;
@Mapper
public interface UnitEntityMapper {
	/**删除方法，根据id删除*/
    int deleteUnitEntity(Long id);
    
    /**增加方法*/
    int insertUnitEntity(UnitEntity record);
    
    /**查询方法,根据id查询*/
    UnitEntity selectById(Long id);
    
    /**更新方法*/
    int updateUnitEntity(UnitEntity record);

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月18日 上午9:52:11
	 */
	List<UnitEntity> getUnitEntity(UnitEntity unitEntity);
	
	/**
	 * 
	 * @Description: 查询教材单元列表
	 * 
	 * @return	List<UnitEntity> 
	 * @author  WangMeng
	 */
	List<BookEntity> getUnitEntityByPage(Page<UnitEntity> page,UnitEntity entity);
}
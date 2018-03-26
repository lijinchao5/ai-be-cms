package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.MenuEntity;
@Mapper
public interface MenuEntityMapper {
    int deleteById(Integer id);

    int insertMenuEntity(MenuEntity record);

    MenuEntity selectById(Integer id);

    int updateById(MenuEntity record);

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月16日 下午1:53:52
	 */
	List<MenuEntity> getUserMenu(MenuEntity menuEntity);

}
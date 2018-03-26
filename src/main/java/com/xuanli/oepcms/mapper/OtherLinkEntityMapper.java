package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.OtherLinkEntity;
import com.xuanli.oepcms.plugins.Page;

@Mapper
public interface OtherLinkEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OtherLinkEntity record);

    int insertSelective(OtherLinkEntity record);

    OtherLinkEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OtherLinkEntity record);

    int updateByPrimaryKey(OtherLinkEntity record);

	/**
	 * 
	 * @Description: 查询列表
	 * 
	 * @return	List<OtherLinkEntity> 
	 * @author  WangMeng
	 */
	List<OtherLinkEntity> getEntityByPage(Page<OtherLinkEntity> page, OtherLinkEntity entity);
}
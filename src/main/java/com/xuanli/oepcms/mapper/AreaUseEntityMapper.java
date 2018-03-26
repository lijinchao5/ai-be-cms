package com.xuanli.oepcms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.AreaUseEntity;
import com.xuanli.oepcms.entity.SchoolEntity;
import com.xuanli.oepcms.plugins.Page;

@Mapper
public interface AreaUseEntityMapper {
    int deleteByPrimaryKey(String id);

    int insert(AreaUseEntity record);

    int insertSelective(AreaUseEntity record);

    AreaUseEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AreaUseEntity record);

    int updateByPrimaryKey(AreaUseEntity record);

    List<AreaUseEntity> findByPageqx(Page<AreaUseEntity> pages, Map<String, Object> map);
    
    List<AreaUseEntity> findByPagecs(Page<AreaUseEntity> pages, Map<String, Object> map);
    
    List<AreaUseEntity> findByPagesf(Page<AreaUseEntity> pages, Map<String, Object> map);
    
    List<AreaUseEntity> findByPage(Map<String, Object> map);
	
    void updateschooldate(AreaUseEntity record);
	
    List<SchoolEntity> findByarea(AreaUseEntity record);
    
    List<AreaUseEntity> findByidAllarea(AreaUseEntity record);
}
package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.AreaEntity;
@Mapper
public interface AreaEntityMapper {
    int deleteByPrimaryKey(String id);

    int insert(AreaEntity record);

    int insertSelective(AreaEntity record);

    AreaEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AreaEntity record);

    int updateByPrimaryKey(AreaEntity record);
    
    List<AreaEntity> select(AreaEntity record);
}
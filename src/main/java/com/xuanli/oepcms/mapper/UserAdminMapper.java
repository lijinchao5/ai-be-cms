package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.UserAdmin;
@Mapper
public interface UserAdminMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserAdmin record);

    int insertSelective(UserAdmin record);

    UserAdmin selectByPrimaryKey(Integer id);

    List<UserAdmin> select(UserAdmin record);
    
    int updateByPrimaryKeySelective(UserAdmin record);

    int updateByPrimaryKey(UserAdmin record);
}
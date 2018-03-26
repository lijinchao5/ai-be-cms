package com.xuanli.oepcms.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.SMSEntity;
@Mapper
public interface SMSEntityMapper {
    int insertSMS(SMSEntity record);
}
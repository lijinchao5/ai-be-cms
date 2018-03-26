/**
 * @fileName:  SMSService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年2月3日 下午2:11:33
 */ 
package com.xuanli.oepcms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.entity.SMSEntity;
import com.xuanli.oepcms.mapper.SMSEntityMapper;

/** 
 * @author  QiaoYu 
 */
@Service
public class SMSService {
	@Autowired
	SMSEntityMapper smsEntityMapper;

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月3日 下午2:14:01
	 */
	public void insertSMS(SMSEntity smsEntity) {
		smsEntityMapper.insertSMS(smsEntity);
	}
}

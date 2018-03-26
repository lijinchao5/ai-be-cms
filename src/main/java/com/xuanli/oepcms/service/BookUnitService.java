/**
 * @fileName:  UnitService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月18日 上午9:48:24
 */
package com.xuanli.oepcms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.entity.UnitEntity;
import com.xuanli.oepcms.mapper.UnitEntityMapper;

/**
 * @author QiaoYu
 */
@Service
public class BookUnitService {
	@Autowired
	UnitEntityMapper unitDao;

	/**
	 * @Description: TODO
	 * @CreateName: QiaoYu
	 * @CreateDate: 2018年1月18日 上午9:51:13
	 */
	public List<UnitEntity> getUnitEntity(UnitEntity unitEntity) {
		return unitDao.getUnitEntity(unitEntity);
	}

}

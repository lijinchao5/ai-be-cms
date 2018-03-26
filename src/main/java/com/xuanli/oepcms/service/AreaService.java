package com.xuanli.oepcms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.entity.AreaEntity;
import com.xuanli.oepcms.mapper.AreaEntityMapper;


@Service
public class AreaService {
	
	@Autowired
	AreaEntityMapper areaEntityMapper;


	public Object findById(String id) {
		AreaEntity areaEntity = new AreaEntity();
		areaEntity.setId(id);
		return areaEntityMapper.select(areaEntity);
	}


	public Object findByParentid(String parentid) {
		AreaEntity areaEntity = new AreaEntity();
		areaEntity.setParentId(parentid);
		return areaEntityMapper.select(areaEntity);
	}

	
}

package com.xuanli.oepcms.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xuanli.oepcms.config.SystemConfig;
import com.xuanli.oepcms.entity.AreaUseEntity;
import com.xuanli.oepcms.entity.SchoolEntity;
import com.xuanli.oepcms.mapper.AreaUseEntityMapper;
import com.xuanli.oepcms.plugins.Page;
import com.xuanli.oepcms.util.JyUtil;


@Service
public class AreaUseService {
	
	@Autowired
	AreaUseEntityMapper areaUseEntityMapper;
	
	@Autowired
	SystemConfig systemConfig;
	
	@Autowired
	QtuserService qtuserService;
	
	public Page<AreaUseEntity> findByPageqx(Page<AreaUseEntity> pages, Map<String, Object> map) {
		pages.setRecords(areaUseEntityMapper.findByPageqx(pages,map));
		return pages;
	}

	public Page<AreaUseEntity> findByPagecs(Page<AreaUseEntity> pages, Map<String, Object> map) {
		pages.setRecords(areaUseEntityMapper.findByPagecs(pages,map));
		return pages;
	}
	
	public Page<AreaUseEntity> findByPagesf(Page<AreaUseEntity> pages, Map<String, Object> map) {
		pages.setRecords(areaUseEntityMapper.findByPagesf(pages,map));
		return pages;
	}
	
	public int updateareaUse(AreaUseEntity areaUseEntity) {
		try {
			List<SchoolEntity> findByarea = areaUseEntityMapper.findByarea(areaUseEntity);
			for (SchoolEntity schoolEntity : findByarea) {
				schoolEntity.setEnddate(areaUseEntity.getEnddate());
			}
			List<AreaUseEntity> list = areaUseEntityMapper.findByidAllarea(areaUseEntity);
			for (AreaUseEntity areaUseEntity2 : list) {
				areaUseEntity2.setStartdate(areaUseEntity.getStartdate());
				areaUseEntity2.setEnddate(areaUseEntity.getEnddate());
				areaUseEntity2.setUpdateid(areaUseEntity.getUpdateid());
				areaUseEntity2.setUpdatedate(areaUseEntity.getUpdatedate());
			}
			
			
			//更新所有学校和区域
			String sendPost = JyUtil.sendPost(systemConfig.QIANTAI_URL+"/syncUser/saveAreaDate.do", "SchoolEntityList="+JSONObject.toJSONString(findByarea)+"&AreaUseEntityList="+JSONObject.toJSONString(list));
			if("1".equals(sendPost)) {
				for (AreaUseEntity areaUseEntity2 : list) {
					//更新区县和市区 省份
					areaUseEntityMapper.deleteByPrimaryKey(areaUseEntity2.getId());
					areaUseEntityMapper.insertSelective(areaUseEntity2);
					//更新学校
					areaUseEntityMapper.updateschooldate(areaUseEntity2);
				}
				return 1;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 0;
	}

	public AreaUseEntity findById(String id) {
		return areaUseEntityMapper.selectByPrimaryKey(id);
	}


	
}

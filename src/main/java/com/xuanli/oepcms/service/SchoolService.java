package com.xuanli.oepcms.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xuanli.oepcms.config.SystemConfig;
import com.xuanli.oepcms.entity.AreaUseEntity;
import com.xuanli.oepcms.entity.SchoolEntity;
import com.xuanli.oepcms.mapper.AreaUseEntityMapper;
import com.xuanli.oepcms.mapper.SchoolEntityMapper;
import com.xuanli.oepcms.plugins.Page;
import com.xuanli.oepcms.util.JyUtil;

@Service
public class SchoolService {
	@Autowired
	SchoolEntityMapper schoolEntityMapper;
	
	@Autowired
	SystemConfig systemConfig;
	
	@Autowired
	AreaUseEntityMapper areaUseEntityMapper;

	public List<SchoolEntity> selectSchoolEntity(SchoolEntity schoolEntity) {
		return schoolEntityMapper.selectSchoolEntity(schoolEntity);
	}

	public int saveSchool(SchoolEntity school) {
		try {
			Integer selectMaxId = schoolEntityMapper.selectMaxId();
			if(selectMaxId!=null) {
				school.setSchoolId(JyUtil.getschool_id()+selectMaxId);
				school.setId((long)selectMaxId);
			}else {
				school.setSchoolId(JyUtil.getschool_id()+"10000");
				school.setId(10000l);
			}
			school.setCreateDate(new Date());
			school.setEnableFlag("T");
			AreaUseEntity areaUseEntity = areaUseEntityMapper.selectByPrimaryKey(school.getAddressArea());
			if(areaUseEntity!=null&&areaUseEntity.getEnddate()!=null) {
				school.setEnddate(areaUseEntity.getEnddate());
				school.setStartdate(areaUseEntity.getStartdate());
			}
			
			String sendPost = JyUtil.sendPost(systemConfig.QIANTAI_URL+"/syncUser/saveSchool.do", "schoolInfo="+JSONObject.toJSONString(school));
			if("1".equals(sendPost)) {
				schoolEntityMapper.insertSchoolEntity(school);
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 0;
	}
	public int updateSchool(SchoolEntity school) {
		try {
			school.setUpdateDate(new Date());
//			AreaUseEntity areaUseEntity = areaUseEntityMapper.selectByPrimaryKey(school.getAddressArea());
//			if(areaUseEntity!=null&&areaUseEntity.getEnddate()!=null) {
//				school.setEnddate(areaUseEntity.getEnddate());
//				school.setStartdate(areaUseEntity.getStartdate());
//			}
			String sendPost = JyUtil.sendPost(systemConfig.QIANTAI_URL+"/syncUser/updateSchool.do", "schoolInfo="+JSONObject.toJSONString(school));
			if("1".equals(sendPost)) {
				schoolEntityMapper.updateSchoolEntity(school);
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 0;
	}
	public int deleteSchool(SchoolEntity school) {
		try {
			school.setEnableFlag("F");
			school.setUpdateDate(new Date());
			String sendPost = JyUtil.sendPost(systemConfig.QIANTAI_URL+"/syncUser/updateSchool.do", "schoolInfo="+JSONObject.toJSONString(school));
			if("1".equals(sendPost)) {
				schoolEntityMapper.updateSchoolEntity(school);
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 0;
	}

	public Page<SchoolEntity> findByPage(Page<SchoolEntity> pages,SchoolEntity schoolEntity) {
		pages.setRecords(schoolEntityMapper.findByPage(pages,schoolEntity));
		return pages;
		
	}

	public Object findDicById(String id) {
		SchoolEntity schoolEntity = schoolEntityMapper.selectById(Long.parseLong(id));
		return schoolEntity;
	}
}

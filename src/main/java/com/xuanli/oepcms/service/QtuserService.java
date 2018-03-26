package com.xuanli.oepcms.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xuanli.oepcms.config.SystemConfig;
import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.mapper.UserEntityMapper;
import com.xuanli.oepcms.plugins.Page;
import com.xuanli.oepcms.util.ConstantUtil;
import com.xuanli.oepcms.util.JyUtil;
import com.xuanli.oepcms.util.PasswordUtil;

@Service
@Transactional
public class QtuserService extends BaseService {
	public final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private UserEntityMapper userDao;
	
	@Autowired
	SystemConfig systemConfig;

	public int saveUser(UserEntity userEntity) {
		userEntity.setCreateDate(new Date());
		userEntity.setEnableFlag("T");
		String sendPost = JyUtil.sendPost(systemConfig.QIANTAI_URL+"/syncUser/saveUser.do", "userInfo="+JSONObject.toJSONString(userEntity));
		if("1".equals(sendPost)) {
			return userDao.insertUserEntity(userEntity);
		}else {
			return 0;
		}
	}

	public List<UserEntity> selectUserEntity(UserEntity userEntity) {
		return userDao.selectUserEntity(userEntity);
	}

	public int resetStudentPassword(UserEntity userEntity) {
		List<UserEntity> selectUserEntity = userDao.selectUserEntity(userEntity);
		if(selectUserEntity.size()>0) {
			UserEntity userEntity2 = selectUserEntity.get(0);
			UserEntity userEntitypost=new UserEntity();
			userEntitypost.setCmsId(userEntity2.getCmsId());
			userEntitypost.setPassword(PasswordUtil.generate(ConstantUtil.USERPASSWORD));
			String sendPost = JyUtil.sendPost(systemConfig.QIANTAI_URL+"/syncUser/updateUser.do", "userInfo="+JSONObject.toJSONString(userEntitypost));
			if("1".equals(sendPost)) {
				userEntity.setPassword(PasswordUtil.generate(ConstantUtil.USERPASSWORD));
				userDao.updateUserEntity(userEntity);
				return 1;
			}
		}
		return 0;
	}

	public UserEntity selectById(Long id) {
		return userDao.selectById(id);
	}

	public int updateUser(UserEntity userEntity) {
		return userDao.updateUserEntity(userEntity);
	}

	public int deleteUserEntity(long id) {
		UserEntity userEntity=new UserEntity();
		userEntity.setId(id);
		List<UserEntity> selectUserEntity = userDao.selectUserEntity(userEntity);
		if(selectUserEntity.size()>0) {
			UserEntity userEntity2 = selectUserEntity.get(0);
			UserEntity userEntitypost=new UserEntity();
			userEntitypost.setCmsId(userEntity2.getCmsId());
			userEntitypost.setEnableFlag("F");
			String sendPost = JyUtil.sendPost(systemConfig.QIANTAI_URL+"/syncUser/updateUser.do", "userInfo="+JSONObject.toJSONString(userEntitypost));
			if("1".equals(sendPost)) {
				userEntity.setEnableFlag("F");
				System.out.println(JSON.toJSONString(userEntity));
				userDao.updateUserEntity(userEntity);
				// userDao.deleteUserEntity(id);
				return 1;
			}
		}
		return 0;
	}

	public Page<UserEntity> findByPage(Page<UserEntity> pages, UserEntity user) {
		pages.setRecords(userDao.findByPage(pages, user));
		return pages;

	}

	public boolean findByName(String name) {
		UserEntity userEntity = new UserEntity();
		userEntity.setName(name);
		List<UserEntity> list = userDao.findByName(userEntity);
		if (list.size() > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public void setUserEnableFlag(String areaid,String enableFlag) {
		UserEntity userEntity = new UserEntity();
		userEntity.setAreaid(areaid);
		List<UserEntity> selectUserEntity = userDao.selectUserEntity(userEntity);
		for (UserEntity userEntity2 : selectUserEntity) {
			UserEntity entity=new UserEntity();
			entity.setCmsId(userEntity2.getCmsId());
			entity.setEnableFlag(enableFlag);
			String sendPost = JyUtil.sendPost(systemConfig.QIANTAI_URL+"/syncUser/updateUser.do", "userInfo="+JSONObject.toJSONString(entity));
			if("1".equals(sendPost)) {
				entity.setId(userEntity2.getId());
				userDao.updateUserEntity(entity);
			}
		}
	}
}

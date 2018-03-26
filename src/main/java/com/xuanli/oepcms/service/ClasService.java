package com.xuanli.oepcms.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuanli.oepcms.entity.ClasEntity;
import com.xuanli.oepcms.entity.UserSchoolEntity;
import com.xuanli.oepcms.mapper.ClasEntityMapper;
import com.xuanli.oepcms.util.PageBean;
import com.xuanli.oepcms.util.StringUtil;


@Service
@Transactional
public class ClasService {
	@Autowired
	private ClasEntityMapper clasDao;
	@Autowired
	private UserService userService;
	
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月16日 上午9:37:05
	 */
	public List<ClasEntity> selectClasEntity(ClasEntity clasEntity) {
		return clasDao.selectClasEntity(clasEntity);
	}
	/**
	 * @Description:  TODO 增加班级
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月16日 下午3:15:02
	 */
	public void saveClas(ClasEntity clasEntity, Long userId) {
		//获取学校id
		List<UserSchoolEntity> userSchoolEntities = userService.getUserSchool(userId);
		Integer schoolId = userSchoolEntities.get(0).getSchoolId().intValue();
		clasEntity.setEnableFlag("T");
		clasEntity.setCreateId(userId.toString());
		clasEntity.setCreateDate(new Date());
		clasEntity.setSchoolId(schoolId);
		//增加班级
		clasDao.insertClasEntity(clasEntity);
		//更新班级编号
		ClasEntity clasEntity2 = new ClasEntity();
		clasEntity2.setId(clasEntity.getId());
		clasEntity2.setClasId(clasEntity.getId().longValue()+StringUtil.getRandomZM(2));
		clasDao.updateById(clasEntity2);
	}
	/**删除班级,只解除老师与班级绑定关系，create_id设置为null*/
	public void updateClas(Long clasId) {
		clasDao.updateClas(clasId);
	}
	
	public ClasEntity selectById(Long clasId) {
		return clasDao.selectById(clasId);
	}
	/**
	 * @Description:  TODO 获取教师创建的班级
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月30日 上午9:34:10
	 */
	public void findClasByPage(ClasEntity clasEntity, PageBean pageBean) {
		int total = clasDao.findClasByPageTotal(clasEntity);
		pageBean.setTotal(total);
		clasEntity.setStart(pageBean.getRowFrom());
		clasEntity.setEnd(pageBean.getPageSize());
		List<ClasEntity> clasEntities = clasDao.findClasByPage(clasEntity);
		pageBean.setRows(clasEntities);
		
	}
}

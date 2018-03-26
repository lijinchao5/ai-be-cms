package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.UserClasEntity;
import com.xuanli.oepcms.entity.UserEntity;
import com.xuanli.oepcms.entity.UserSchoolEntity;
import com.xuanli.oepcms.plugins.Page;
@Mapper
public interface UserEntityMapper {
    /**
	 * @Description:  TODO 删除方法 只能按照id删除
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月15日 下午2:08:38
	 */
    int deleteUserEntity(Long id);
    /**
     * @Description:  TODO 增加方法
     * @CreateName:  QiaoYu 
     * @CreateDate:  2018年1月15日 下午2:08:48
     */
    int insertUserEntity(UserEntity record);
    /**
     * @Description:  TODO 按照id查询
     * @CreateName:  QiaoYu 
     * @CreateDate:  2018年1月15日 下午2:09:18
     */
    UserEntity selectById(Long id);
    /**
     * @Description:  TODO 按照id 更新
     * @CreateName:  QiaoYu 
     * @CreateDate:  2018年1月15日 下午2:09:28
     */
    int updateUserEntity(UserEntity record);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月15日 下午2:15:25
	 */
	List<UserEntity> selectUserEntity(UserEntity userEntity);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月15日 下午2:25:09
	 */
	List<UserEntity> login(UserEntity userEntity);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月15日 下午5:11:32
	 */
	int insertUserSchool(UserSchoolEntity userSchoolEntity);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月16日 上午9:42:23
	 */
	int inserUserClas(UserClasEntity userClasEntity);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月16日 下午2:13:43
	 */
	int findStudentByPageTotal(UserEntity userEntity);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月16日 下午2:13:49
	 */
	List<UserEntity> findStudentByPage(UserEntity userEntity);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月16日 下午2:44:24
	 */
	void deleteUserClas(UserClasEntity userClasEntity);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月16日 下午2:56:22
	 */
	int getClasStudentsByTeacher(Long teacherId);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月16日 下午3:26:25
	 */
	List<UserSchoolEntity> getUserSchool(Long userId);
	/**
	 * @Description:  TODO 导出列表信息
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月17日 上午10:02:26
	 */
	List<UserEntity> exportNameNum(UserEntity userEntity);
	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月18日 下午4:40:04
	 */
	List<UserEntity> getClasUserStudent(UserEntity userEntity);

	List<UserEntity> findByPage(Page<UserEntity> pages,UserEntity userEntity);
	
	
	List<UserEntity> findByName(UserEntity userEntity);
	
	
}
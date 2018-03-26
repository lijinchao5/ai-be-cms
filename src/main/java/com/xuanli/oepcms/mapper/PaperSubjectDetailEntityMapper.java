package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.PaperSubjectDetailEntity;
import com.xuanli.oepcms.plugins.Page;
@Mapper
public interface PaperSubjectDetailEntityMapper {
	/**
	 * 
	 * Title: deletePaperSubjectDetailEntity 
	 * Description:   删除试卷题详情方法
	 * @date 2018年2月2日 上午11:49:55
	 * @param id
	 * @return
	 */
    int deletePaperSubjectDetailEntity(Long id);
    /**
     * 
     * Title: insertPaperSubjectDetailEntity 
     * Description:   新增方法
     * @date 2018年2月2日 上午11:50:44
     * @param record
     * @return
     */
    int insertPaperSubjectDetailEntity(PaperSubjectDetailEntity record);
    /**
     * 
     * Title: selectById 
     * Description:   查询方法
     * @date 2018年2月2日 上午11:50:57
     * @param id
     * @return
     */
    PaperSubjectDetailEntity selectById(Long id);
    /**
     * 
     * Title: updatePaperSubjectDetailEntity 
     * Description:   更新方法
     * @date 2018年2月2日 上午11:51:03
     * @param record
     * @return
     */
    int updatePaperSubjectDetailEntity(PaperSubjectDetailEntity record);

	/**
	 * 
	 * @Description: 查询列表
	 * 
	 * @return	List<PaperSubjectDetailEntity> 
	 * @author  WangMeng
	 */
	List<PaperSubjectDetailEntity> getEntityByPage(Page<PaperSubjectDetailEntity> page,PaperSubjectDetailEntity entity);

}
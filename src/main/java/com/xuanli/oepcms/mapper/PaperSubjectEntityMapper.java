package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.PaperSubjectEntity;
import com.xuanli.oepcms.plugins.Page;
@Mapper
public interface PaperSubjectEntityMapper {
	/**
	 * 
	 * Title: deletePaperSubjectEntity 
	 * Description:   删除试卷题方法
	 * @date 2018年2月2日 上午11:51:31
	 * @param id
	 * @return
	 */
    int deletePaperSubjectEntity(Long id);
    /**
     * 
     * Title: insertPaperSubjectEntity 
     * Description:   新增方法
     * @date 2018年2月2日 上午11:51:34
     * @param record
     * @return
     */
    int insertPaperSubjectEntity(PaperSubjectEntity record);
    /**
     * 
     * Title: selectByPrimaryKey 
     * Description:   查询方法
     * @date 2018年2月2日 上午11:51:36
     * @param id
     * @return
     */
    PaperSubjectEntity selectById(Long id);
    /**
     * 
     * Title: updateByPaperSubjectEntity 
     * Description:   更新方法
     * @date 2018年2月2日 上午11:51:38
     * @param record
     * @return
     */
    int updatePaperSubjectEntity(PaperSubjectEntity record);
    


	/**
	 * 
	 * @Description: 查询列表
	 * 
	 * @return	List<PaperSubjectEntity> 
	 * @author  WangMeng
	 */
	List<PaperSubjectEntity> getEntityByPage(Page<PaperSubjectEntity> page,PaperSubjectEntity entity);


}
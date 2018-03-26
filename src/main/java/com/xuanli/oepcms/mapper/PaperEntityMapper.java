package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.PaperEntity;
import com.xuanli.oepcms.plugins.Page;
@Mapper
public interface PaperEntityMapper {
	/**
	 * 
	 * Title: deletePaperEntity 
	 * Description:   删除试卷方法
	 * @date 2018年2月2日 上午11:46:59
	 * @param id
	 * @return
	 */
    int deletePaperEntity(Long id);
    /**
     * 
     * Title: insertPaperEntity 
     * Description:   增加试卷方法
     * @date 2018年2月2日 上午11:47:28
     * @param record
     * @return
     */
    int insertPaperEntity(PaperEntity record);
    /**
     * 
     * Title: selectByID 
     * Description:   查询试卷方法
     * @date 2018年2月2日 上午11:47:39
     * @param id
     * @return
     */
    PaperEntity selectByID(Long id);
    /**
     * 
     * Title: updatePaperEntity 
     * Description:   更新试卷方法
     * @date 2018年2月2日 上午11:47:54
     * @param record
     * @return
     */
    int updatePaperEntity(PaperEntity record);
    

	/**
	 * 
	 * @Description: 查询列表
	 * 
	 * @return	List<PaperEntity> 
	 * @author  WangMeng
	 */
	List<PaperEntity> getEntityByPage(Page<PaperEntity> page,PaperEntity entity);

}
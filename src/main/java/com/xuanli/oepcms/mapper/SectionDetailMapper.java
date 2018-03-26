package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.SectionDetail;
import com.xuanli.oepcms.plugins.Page;
@Mapper
public interface SectionDetailMapper {

    SectionDetail selectById(Long id);
    /**查询方法,根据id查询*/
    
    /**更新方法*/
    int updateSectionDetail(SectionDetail record);

	/**
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月18日 上午10:15:32
	 */
	List<SectionDetail> getSectionDetails(SectionDetail sectionDetail);
	
	/**
	 * 
	 * @Description: 批量插入
	 * 
	 * @return	int 
	 * @author  WangMeng
	 */
	int insertSectionDetails(List<SectionDetail> list);
	
	/**
	 * 
	 * @Description: 查询列表
	 * 
	 * @return	List<SectionDetail> 
	 * @author  WangMeng
	 */
	List<SectionDetail> getSectionDetailByPage(Page<SectionDetail> page,SectionDetail entity);
}
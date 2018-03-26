package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.ReadSentence;
import com.xuanli.oepcms.plugins.Page;
@Mapper
public interface ReadSentenceMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(ReadSentence record);

    int insertSelective(ReadSentence record);

    ReadSentence selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ReadSentence record);

    int updateByPrimaryKey(ReadSentence record);

	/**
	 * 
	 * @Description: 批量插入
	 * 
	 * @return	int 
	 * @author  WangMeng
	 */
	int insertBatch(List<ReadSentence> list);
	
	/**
	 * 
	 * @Description: 查询列表
	 * 
	 * @return	List<ReadSentence> 
	 * @author  WangMeng
	 */
	List<ReadSentence> getEntityByPage(Page<ReadSentence> page,ReadSentence entity);
}
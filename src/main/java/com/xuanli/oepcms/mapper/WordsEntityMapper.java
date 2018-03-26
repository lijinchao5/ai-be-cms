package com.xuanli.oepcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.entity.WordsEntity;
import com.xuanli.oepcms.plugins.Page;
@Mapper
public interface WordsEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WordsEntity record);

    int insertSelective(WordsEntity record);

    WordsEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WordsEntity record);

    int updateByPrimaryKey(WordsEntity record);
    

	/**
	 * 
	 * @Description: 批量插入
	 * 
	 * @return	int 
	 * @author  WangMeng
	 */
	int insertWordsBatch(List<WordsEntity> list);


	/**
	 * 
	 * @Description: 验证是否已存在
	 * 
	 * @return	int 
	 * @author  WangMeng
	 */
    int repeatValid(WordsEntity record);

	/**
	 * 
	 * @Description: 查询列表
	 * 
	 * @return	List<WordsEntity> 
	 * @author  WangMeng
	 */
	List<WordsEntity> getEntityByPage(Page<WordsEntity> page,WordsEntity entity);
}
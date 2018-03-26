/**
 * @fileName:  BookService.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年1月18日 上午9:31:10
 */ 
package com.xuanli.oepcms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xuanli.oepcms.entity.BookEntity;
import com.xuanli.oepcms.mapper.BookEntityMapper;

/** 
 * @author  QiaoYu 
 */
@Service
public class BookService {
	@Autowired
	BookEntityMapper bookDao;
	
	/**
	 * @Description:  TODO 获取书本的信息
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年1月18日 上午9:31:53
	 */
	public List<BookEntity> getBookEntity(BookEntity bookEntity) {
		return bookDao.getBookEntity(bookEntity);
	}
	
	
	
}

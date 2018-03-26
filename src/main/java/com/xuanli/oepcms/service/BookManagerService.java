package com.xuanli.oepcms.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuanli.oepcms.entity.BookEntity;
import com.xuanli.oepcms.entity.BookVersionEntity;
import com.xuanli.oepcms.entity.SectionDetail;
import com.xuanli.oepcms.entity.SectionEntity;
import com.xuanli.oepcms.entity.UnitEntity;
import com.xuanli.oepcms.mapper.BookEntityMapper;
import com.xuanli.oepcms.mapper.BookVersionEntityMapper;
import com.xuanli.oepcms.mapper.SectionDetailMapper;
import com.xuanli.oepcms.mapper.SectionEntityMapper;
import com.xuanli.oepcms.mapper.UnitEntityMapper;
import com.xuanli.oepcms.plugins.Page;
import com.xuanli.oepcms.util.AliOSSUtil;

/** 
 * 
 * @ClassName: BookManagerService  
 * @Description: 教材管理
 * @author WangMeng  
 * @date 2018年2月6日  
 *
 */
@Service
public class BookManagerService {
	
	@Autowired
	BookEntityMapper bookDao;
	
	@Autowired
	UnitEntityMapper unitDao;
	
	@Autowired
	SectionEntityMapper sectionDao;
	
	@Autowired
	SectionDetailMapper sectionDetailDao;
	
	@Autowired
	BookVersionEntityMapper bookVersionMapper;
	
	@Autowired
	AliOSSUtil aliOSSUtil;
	
	/**
	 * 
	 * @Description: 教材导入
	 * 
	 * @return	int 
	 * @author  WangMeng
	 */
	@Transactional
	public int saveBook(BookEntity bookEntity) throws IOException {

		//将之前版本教材逻辑删除
		bookDao.coverBook(bookEntity);
		bookDao.insertBookEntity(bookEntity);
		
		String timestamp = System.currentTimeMillis()+"";

		List<UnitEntity> unitlist = bookEntity.getList();
		for (UnitEntity unit : unitlist) {
			
			unit.setBookId(bookEntity.getId()+"");
			unit.setCreateId(bookEntity.getCreateId());
			unit.setCreateDate(bookEntity.getCreateDate());
			unitDao.insertUnitEntity(unit);
			
			List<SectionEntity> sectionlist = unit.getList();
			for (SectionEntity section : sectionlist) {

				section.setUnitId(unit.getId()+"");
				section.setCreateId(bookEntity.getCreateId());
				section.setCreateDate(bookEntity.getCreateDate());
				sectionDao.insertSectionEntity(section);
				
				List<SectionDetail> details = section.getList();
				if(details.size()>0) {
					
					for (SectionDetail detail : details) {
						detail.setSectionId(section.getId()+"");
						detail.setCreateId(bookEntity.getCreateId());
						detail.setCreateDate(bookEntity.getCreateDate());
						
						
                    	//声音，女声，男声，图片
                    	String audioPath = detail.getAudioPath();
                    	String mAudioPath = detail.getmAudioPath();
                    	String wAudioPath = detail.getwAudioPath();
                    	String picturePath = detail.getPicturePath();
						
                    	//调用阿里云存储
                    	if(StringUtils.isNotBlank(audioPath)) {
                    		audioPath = aliOSSUtil.uploadFile(new FileInputStream(new File(audioPath)),
                    				timestamp , "mp3");
                    	}
                    	if(StringUtils.isNotBlank(mAudioPath)) {
                    		mAudioPath = aliOSSUtil.uploadFile(new FileInputStream(new File(mAudioPath)), 
                    				timestamp , "mp3");
                    	}
                    	if(StringUtils.isNotBlank(wAudioPath)) {
                    		wAudioPath = aliOSSUtil.uploadFile(new FileInputStream(new File(wAudioPath)), 
                    				timestamp , "mp3");
                    	}
                    	if(StringUtils.isNotBlank(picturePath)) {
                    		picturePath = aliOSSUtil.uploadFile(new FileInputStream(new File(picturePath)), 
                    				timestamp , "jpg");
                    	}
                    	
                    	detail.setAudioPath(audioPath);
                    	detail.setmAudioPath(mAudioPath);
                    	detail.setwAudioPath(wAudioPath);
                    	detail.setPicturePath(picturePath);
					}
					
					sectionDetailDao.insertSectionDetails(details);
				}
			}
		}
		return 1;
	}

	/**
	 * 
	 * @Description: 获取教材列表
	 * 
	 * @return	int 
	 * @author  WangMeng
	 */
	public Map<String, Object> getBookContent(String bookId) {
		
		Map<String, Object> map = new HashMap<>();
		
		UnitEntity u = new UnitEntity();
		u.setBookId(bookId);
		map.put("units", unitDao.getUnitEntityByPage(Page.noPageBean(), u));
		
		SectionEntity s = new SectionEntity();
		s.setBookId(bookId);
		map.put("sections", sectionDao.getSectionEntityByPage(Page.noPageBean(), s));
		
		SectionDetail sd = new SectionDetail();
		sd.setBookId(bookId);
		map.put("sectiondetails", sectionDetailDao.getSectionDetailByPage(Page.noPageBean(), sd));
		
		return map;
	}
	
	
	/**
	 * 
	 * @Description: 教材重复验证
	 * 
	 * @return	int 
	 * @author  WangMeng
	 */
	public int repeatValid(BookEntity bookEntity) {
		return bookDao.repeatValid(bookEntity);
	}

	/**
	 * 
	 * @Description: 获取教材列表
	 * 
	 * @return	int 
	 * @author  WangMeng
	 */
	public Page<BookEntity> getBookEntityByPage(Page<BookEntity> page, BookEntity bookEntity) {
		page.setRecords(bookDao.getBookEntityByPage(page,bookEntity));
		return page;
	}

	/**
	 * 
	 * @Description: 教材版本修改
	 * 
	 * @return	int 
	 * @author  WangMeng
	 */
	@Transactional
	public int updateBookVersion(List<BookVersionEntity> list, String version_val) {
		bookVersionMapper.deleteOldval(version_val);
		if(list.size()==0) return 1;
		return bookVersionMapper.insertBookVersion(list);
	}
	
	/**
	 * 
	 * @Description: 获取教材版本列表
	 * 
	 * @return	int 
	 * @author  WangMeng
	 */
	public Page<BookVersionEntity> getBookVersionByPage(Page<BookVersionEntity> page, BookVersionEntity bookEntity) {
		page.setRecords(bookVersionMapper.getBookVersionByPage(page,bookEntity));
		return page;
	}
	
}

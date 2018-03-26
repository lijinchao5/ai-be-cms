package com.xuanli.oepcms.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.csvreader.CsvReader;
import com.xuanli.oepcms.entity.SectionDetail;
import com.xuanli.oepcms.entity.SectionEntity;
import com.xuanli.oepcms.entity.UnitEntity;
import com.xuanli.oepcms.exception.ServiceException;

/**
 * 
 * @ClassName: ResourceImportUtil
 * @Description: 基础资源导入工具类
 * @author WangMeng
 * @date 2018年2月9日
 *
 */
public class ResourceImportBookUtil {

//	public static void main(String[] args) throws Exception {
//		
//		String folderPath = getfolderPath("D:\\人教版（三起）六年级下册\\");
//		
//		List<UnitEntity> units = getUnitTreeByWord(folderPath);
//		
//		List<UnitEntity> list = getSectionDetailByFolder(folderPath, units);
//		
//		System.out.println(JSON.toJSONString(list));
//		String str = "src\\main\\webapp\\upload\\1519811257533\\三起三上\\module1\\module2_pictures\\words\\byebye";
//		System.out.println(str.substring(str.indexOf("webapp\\upload")+14));
//	}
	
	public static String getfolderPath(String folderPath) throws Exception {
		

		File mianFolder = new File(folderPath);
		
		//判断当前目录是否是根目录
		String[] mainlist = mianFolder.list();
		for (String string : mainlist) {
			if(string.toLowerCase().contains("所属课") && string.toLowerCase().contains(".docx")) return folderPath;
		}
		//判断二级目录是否是根目录
		File[] listFiles = mianFolder.listFiles();
		for (File file : listFiles) {
			if(file.isDirectory()) {
				
				String[] list = file.list();
				for (String string : list) {
					if(string.toLowerCase().contains("所属课") && string.toLowerCase().contains(".docx")) return file.getCanonicalPath();
				}
			}
		}
		throw new ServiceException("关键文件【所属课.docx】不存在");
	}
	
	
	/**
	 * 
	 * @Description: 获取单元小节列表
	 * 
	 * @return	List<UnitEntity> 
	 * @author  WangMeng
	 * @throws IOException 
	 */
	public static List<UnitEntity> getUnitTreeByWord(String folderPath) throws Exception {

		
		String wordfilePath = folderPath + File.separator + "所属课信息.docx";
		List<UnitEntity> units = new ArrayList<>();
		try {
			
			File file = new File(folderPath);
			File[] files = file.listFiles();
			for (File f : files) {
				if(f.getName().toLowerCase().contains("所属课") && f.getName().toLowerCase().contains(".docx")) {
					wordfilePath = f.getCanonicalPath();
					break;
				}
			}
			
			List<List<String>> list = ResourceImportUtil.readDocxTable(wordfilePath);
	//		list.remove(0);//去除表头
			
			
			UnitEntity ue = null;
			for (List<String> row : list) {
				
				String col1 = row.get(0);
				String col2 = row.get(1);
				
				//忽略复习章节
				if(StringUtils.isBlank(col1) || col1.toLowerCase().contains("revision") 
						|| col1.toLowerCase().contains("picture dictionary") 
						|| col1.toLowerCase().contains("单元目录") 
						|| col1.toLowerCase().contains("教材课程信息")) continue;
				
				//col2：空-unit，不空-lesson
				if(StringUtil.isEmpty(col2)) {
					ue = new UnitEntity();
					ue.setName(col1);
					ue.setEnableFlag("T");
					ue.setList(new ArrayList<>());
					units.add(ue);
				}else {
					SectionEntity se = new SectionEntity();
					se.setName(col1);
					se.setEnableFlag("T");
					se.setBianhao(Integer.parseInt(col2.replaceAll("\\s*", "")));
					se.setList(new ArrayList<>());
					ue.getList().add(se);
				}
			}
		} catch (Exception e) {
			throw new ServiceException("所属课信息.docx解析失败");
		}
		return units;
	}
	
	public static List<UnitEntity> getSectionDetailByFolder(String folderPath,List<UnitEntity> units) throws Exception {
		
		
		for (UnitEntity unit : units) {
			
			//小节列表
			List<SectionEntity> sections = unit.getList();
			//单元文件夹
			String unitForderName = unit.getName().toLowerCase().replaceAll("\\s*", "");
			//当前单元信息
			String unitInfoFileName = unitForderName+"_info.txt";
			

			//目录下文件列表
			File unitFolder = new File(folderPath+File.separator+unitForderName);
			//文件夹不存在跳过循环
			if(!unitFolder.exists()) continue;
			
			//获取单元名称
			File infoFile = new File(folderPath+File.separator+unitForderName+File.separator+unitInfoFileName);
			if(infoFile.exists()) {
				FileInputStream infoStream = null;
				InputStreamReader infoStreamReader = null;
				BufferedReader br = null;
				try {
					infoStream = new FileInputStream(infoFile);
					infoStreamReader = new InputStreamReader(infoStream, "GBK");
					br = new BufferedReader(infoStreamReader);
					String lineTxt = null;
					while ((lineTxt = br.readLine()) != null) {
						if(lineTxt!=null && lineTxt.contains("unit_text")) {
							lineTxt = lineTxt.replaceAll("unit_text:", "").trim();
							break;
						}
					}
					infoFile.delete();
					unit.setName(lineTxt);
					

					br.close();
					infoStreamReader.close();
					infoStream.close();

				} catch (Exception e) {

					br.close();
					infoStreamReader.close();
					infoStream.close();
					throw e;
				}
				
			}
			

			//目录下文件列表
			File[] fileList = unitFolder.listFiles();
			
			//读取目录下文件
			for (int i = 0; i < fileList.length; i++) {
		        if (fileList[i].isFile()) {
		        	File _file = fileList[i];
		        	String fileName = _file.getName().toLowerCase();
		        	if(fileName.contains(".csv")) {
		        		
		        		FileInputStream csvInputStream = null;
		        		CsvReader csv = null;
		        		try {
							

			        		csvInputStream = new FileInputStream(_file);
			        		csv = new CsvReader(csvInputStream,Charset.forName("gbk"));
		        			// 读表头
		        			csv.readHeaders();
			        		
		        			//行号
		        			int line = 1;
			        		if(fileName.contains("dialog")) {
			        			
			        			//对话
			                    while (csv.readRecord()){
			                    	if(StringUtil.isEmpty(csv.get(0))) break;line++;
			                    	
			                    	SectionDetail sd = new SectionDetail();
			                    	sd.setType("4");
			                    	sd.setEnableFlag("T");
			                    	
			                    	//句子
			                    	sd.setText(csv.get(0));
			                    	//角色
			                    	sd.setName(csv.get("角色"));
			                    	//句义
			                    	sd.setWordMean(csv.get("句义"));
			                    	//是否重点词汇
			                    	if("是".equalsIgnoreCase(csv.get("是否重点句型"))) sd.setPointType("1");
			                    	else sd.setPointType("0");
			                    	
			                    	String audioPath = csv.get("对应音频");
			                    	String picturePath = csv.get("对应图片");
			                    	//音频
			                    	if(StringUtils.isNotBlank(audioPath)) {
			                    		//拼接路径
			                    		String path = folderPath+File.separator+unitForderName+File.separator
			                    				+audioPath.replace('\\',File.separatorChar);
			                    		//判断音频文件是否存在
			                    		String type = ResourceImportUtil.hanFlie(path, audioPath, 1, fileName+"，行数："+line);
			                    		//设置路径
			                    		sd.setAudioPath(path+type);
			                    	}

			                    	//图片
			                    	if(StringUtils.isNotBlank(picturePath)) {
			                    		//拼接路径
			                    		String path = folderPath+File.separator+unitForderName+File.separator
			                    				+picturePath.replace('\\',File.separatorChar);
			                    		//判断图片文件是否存在
			                    		String type = ResourceImportUtil.hanFlie(path, picturePath, 2, fileName+"，行数："+line);
			                    		//设置路径
			                    		sd.setPicturePath(path+type);
			                    	}
			                    	
			                    	
			                    	//设置对话分组
			                    	String _f = fileName.replace(".csv", "");
			                    	try {
			                    		Integer valueOf = Integer.valueOf(_f.split("_")[_f.split("_").length-1].replaceAll("\\s*", ""));
			                    		
			                    		sd.setDialogNum(valueOf);
									} catch (Exception e) {
			                    		sd.setDialogNum(0);
									}

			                    	int index = -1;
			                    	try {
			                    		index = Integer.parseInt(csv.get("所属课").replaceAll("\\s*", ""));
									} catch (Exception e) {
										e.printStackTrace();
										throw new ServiceException("所属课信息错误【"+fileName+"，行数："+line+"】");
									}
			                    	
			                    	//获取所在小节
			                    	SectionEntity se = getSectionEntityByBianhao(sections, index);
			                    	//设置排序号
			                    	sd.setOrderNum(se.getList().size());
			                    	se.getList().add(sd);
			                    }
			        			
			        		}else if(fileName.contains("text") || fileName.contains("letter")) {
			        			//课文
			                    while (csv.readRecord()){
			                    	if(StringUtil.isEmpty(csv.get(0))) break;line++;
			                    	
			                    	SectionDetail sd = new SectionDetail();
			                    	sd.setType("3");
			                    	sd.setEnableFlag("T");
			                    	//句子
			                    	sd.setText(csv.get(0));
			                    	//句义
			                    	sd.setWordMean(csv.get("句义"));
			                    	//是否重点词汇
			                    	if("是".equalsIgnoreCase(csv.get("是否重点句型"))) sd.setPointType("1");
			                    	else sd.setPointType("0");

			                    	String audioPath = csv.get("对应音频");
			                    	String picturePath = csv.get("对应图片");
			                    	

			                    	//音频
			                    	if(StringUtils.isNotBlank(audioPath)) {
			                    		//拼接路径
			                    		String path = folderPath+File.separator+unitForderName+File.separator
			                    				+audioPath.replace('\\',File.separatorChar);
			                    		//判断音频文件是否存在
			                    		String type = ResourceImportUtil.hanFlie(path, audioPath, 1, fileName+"，行数："+line);
			                    		//设置路径
			                    		sd.setAudioPath(path+type);
			                    	}

			                    	//图片
			                    	if(StringUtils.isNotBlank(picturePath)) {
			                    		//拼接路径
			                    		String path = folderPath+File.separator+unitForderName+File.separator
			                    				+picturePath.replace('\\',File.separatorChar);
			                    		//判断图片文件是否存在
			                    		String type = ResourceImportUtil.hanFlie(path, picturePath, 2, fileName+"，行数："+line);
			                    		//设置路径
			                    		sd.setPicturePath(path+type);
			                    	}
			                    	
			                    	int index = -1;
			                    	try {
			                    		index = Integer.parseInt(csv.get("所属课").replaceAll("\\s*", ""));
									} catch (Exception e) {
										e.printStackTrace();
										throw new ServiceException("所属课信息错误【"+fileName+"，行数："+line+"】");
									}
			                    	
			                    	//获取所在小节
			                    	SectionEntity se = getSectionEntityByBianhao(sections,index);
			                    	//设置排序号
			                    	sd.setOrderNum(se.getList().size());
			                    	se.getList().add(sd);
			                    }

			        		}else if(fileName.contains("words")) {
			        			
			                    while (csv.readRecord()){
			                    	
			                    	if(StringUtil.isEmpty(csv.get(0))) break;line++;
			                    	
			                    	SectionDetail sd = new SectionDetail();
			                    	sd.setType("1");
			                    	sd.setEnableFlag("T");
			                    	//单词
			                    	sd.setText(csv.get(0));
			                    	//词义
			                    	sd.setWordMean(csv.get(2));
			                    	//是否重点词汇
			                    	if("是".equalsIgnoreCase(csv.get("是否重点词汇"))) sd.setPointType("1");
			                    	else sd.setPointType("0");
			                    	
			                    	//女声，男声，图片
			                    	String mAudioPath = csv.get("词汇对应音频（男声）");
			                    	String wAudioPath = csv.get("词汇对应音频（女声）");
			                    	String picturePath = csv.get("词汇对应图片");
			                    	
			                    	if(StringUtils.isBlank(mAudioPath) && StringUtils.isBlank(wAudioPath)) 
			                    		throw new ServiceException("单词对应音频必须存在【"+fileName+"，行数："+line+"】"+mAudioPath+";"+wAudioPath);
			                    	
			                    	//男声
			                    	if(StringUtils.isNotBlank(mAudioPath)) {
			                    		//拼接路径
			                    		String path = folderPath+File.separator+unitForderName+File.separator
			                    				+mAudioPath.replace('\\',File.separatorChar);
			                    		//判断音频文件是否存在
			                    		String type = ResourceImportUtil.hanFlie(path, mAudioPath, 1, fileName+"，行数："+line);
			                    		//设置路径
			                    		sd.setmAudioPath(path+type);
			                    	}

			                    	//女声
			                    	if(StringUtils.isNotBlank(wAudioPath)) {
			                    		//拼接路径
			                    		String path = folderPath+File.separator+unitForderName+File.separator
			                    				+wAudioPath.replace('\\',File.separatorChar);
			                    		//判断音频文件是否存在
			                    		String type = ResourceImportUtil.hanFlie(path, wAudioPath, 1, fileName+"，行数："+line);
			                    		//设置路径
			                    		sd.setwAudioPath(path+type);
			                    	}

			                    	//图片
			                    	if(StringUtils.isNotBlank(picturePath)) {
			                    		//拼接路径
			                    		String path = folderPath+File.separator+unitForderName+File.separator
			                    				+picturePath.replace('\\',File.separatorChar);
			                    		//判断图片文件是否存在
			                    		String type = ResourceImportUtil.hanFlie(path, picturePath, 2, fileName+"，行数："+line);
			                    		//设置路径
			                    		sd.setPicturePath(path+type);
			                    	}
			                    	
			                    	int index = -1;
			                    	try {
			                    		index = Integer.parseInt(csv.get("所属课").replaceAll("\\s*", ""));
									} catch (Exception e) {
										e.printStackTrace();
										throw new ServiceException("所属课信息错误【"+fileName+"，行数："+line+"】");
									}
			                    	
			                    	//获取所在小节
			                    	SectionEntity se = getSectionEntityByBianhao(sections, index);
			                    	//设置排序号
			                    	sd.setOrderNum(se.getList().size());
			                    	se.getList().add(sd);
			                    }
			        		}

		                    csv.close();
		                    csvInputStream.close();
		        			
						} catch (Exception e) {
							
							//关闭流
		                    if(csv!=null) csv.close();
		                    if(csvInputStream!=null) csvInputStream.close();
							throw e;
						}
		        		
		        	}
		        }
		    }
		}
		return units;
	}
	
	public static SectionEntity getSectionEntityByBianhao(List<SectionEntity> entities, int bianhao) {
		
		for (SectionEntity sectionEntity : entities) {
			if(sectionEntity.getBianhao() == bianhao) return sectionEntity;
		}
		return entities.get(0);
	}
}

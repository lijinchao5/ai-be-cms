package com.xuanli.oepcms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.xuanli.oepcms.entity.PaperEntity;
import com.xuanli.oepcms.entity.PaperOptionEntity;
import com.xuanli.oepcms.entity.PaperSubjectDetailEntity;
import com.xuanli.oepcms.entity.PaperSubjectEntity;
import com.xuanli.oepcms.exception.ServiceException;

/**
 * 
 * @ClassName: ResourceImportUtil
 * @Description: 基础资源导入工具类
 * @author WangMeng
 * @date 2018年2月9日
 *
 */
public class ResourceImportPaperUtil {

//	public static void main(String[] args) throws Exception {
//		String folderPath = getfolderPath("D:\\试题导入");
//		PaperEntity obj = readXlsx(folderPath);
//		System.out.println(JSON.toJSONString(obj));
//	}

	public static String getfolderPath(String folderPath) throws Exception {
		
		File mianFolder = new File(folderPath);
		//判断当前目录是否是根目录
		String[] mainlist = mianFolder.list();
		for (String string : mainlist) {
			if(string.toLowerCase().contains("paper.xlsx")) return folderPath;
		}
		//判断二级目录是否是根目录
		File[] listFiles = mianFolder.listFiles();
		for (File file : listFiles) {
			if(file.isDirectory()) {
				
				String[] list = file.list();
				for (String string : list) {
					if(string.toLowerCase().contains("paper.xlsx")) return file.getCanonicalPath();
				}
			}
		}
		throw new ServiceException("关键文件paper.xlsx不存在！");
	}
	

	public static PaperEntity readXlsx(String path) throws Exception {
		
		InputStream is = null;
		XSSFWorkbook xssfWorkbook = null;

		PaperEntity obj = new PaperEntity();
		obj.setList(new ArrayList<>());
		
		try {
			
			is = new FileInputStream(path+File.separator+"paper.xlsx");
			xssfWorkbook = new XSSFWorkbook(is);
			int numberOfSheets = xssfWorkbook.getNumberOfSheets();
			numberOfSheets=1;
			for (int numSheet = 0; numSheet < numberOfSheets; numSheet++) {
				//获取当前sheet
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				if (xssfSheet == null) {
					continue;
				}
				
				//获取第二行的试卷基本信息
				
				XSSFRow infoRow = xssfSheet.getRow(1);
				if (infoRow != null) {
					
					String name			= getValue(infoRow.getCell(0));
					String notice		= getValue(infoRow.getCell(1));
					String totalTime	= getValue(infoRow.getCell(2));
					String gradeLevel	= getValue(infoRow.getCell(3));
					String term			= getValue(infoRow.getCell(4));
//					String address		= getValue(infoRow.getCell(5));
					String paperType	= getValue(infoRow.getCell(6));
					String questionType	= getValue(infoRow.getCell(7));
					
					if(StringUtils.isNotBlank(name)
//							&& StringUtils.isNotBlank(notice)
							&& StringUtils.isNotBlank(totalTime) && StringUtils.isNotBlank(gradeLevel)
							&& StringUtils.isNotBlank(term)
							&& StringUtils.isNotBlank(paperType) && StringUtils.isNotBlank(questionType)) {
						
						obj.setPaperNum(System.currentTimeMillis()+"");
						obj.setName(name);
						obj.setNotice(notice);
//						地址在页面手动选择
//						obj.setAddressProvinceName(address);
						
						//考试时间转换为秒
						try {
							String[] timearr = totalTime.split(":");
							if(timearr.length==1) {
								int s = Integer.parseInt(timearr[0]);
								obj.setTotalTime(s);
							}else if(timearr.length==2) {
								int m = Integer.parseInt(timearr[0]);
								int s = Integer.parseInt(timearr[1]);
								obj.setTotalTime(s + m*60);
							}else if(timearr.length==3) {
								int h = Integer.parseInt(timearr[0]);
								int m = Integer.parseInt(timearr[1]);
								int s = Integer.parseInt(timearr[2]);
								obj.setTotalTime(s + m*60 + h*60*60);
							}
						} catch (Exception e) {
							throw new ServiceException("试卷模板时长填写不规范（"+totalTime+"），正确格式 分:秒，例如 25:30");
						}
						
						
						//年级
						gradeLevel = gradeLevel.replaceAll("\\s*", "");
						if("一年级".equals(gradeLevel)) {
							obj.setGradeLevelId(1);
						}else if("二年级".equals(gradeLevel)) {
							obj.setGradeLevelId(2);
						}else if("三年级".equals(gradeLevel)) {
							obj.setGradeLevelId(3);
						}else if("四年级".equals(gradeLevel)) {
							obj.setGradeLevelId(4);
						}else if("五年级".equals(gradeLevel)) {
							obj.setGradeLevelId(5);
						}else if("六年级".equals(gradeLevel)) {
							obj.setGradeLevelId(6);
						}else if("七年级".equals(gradeLevel)) {
							obj.setGradeLevelId(7);
						}else if("八年级".equals(gradeLevel)) {
							obj.setGradeLevelId(8);
						}else if("九年级".equals(gradeLevel)) {
							obj.setGradeLevelId(9);
						}else if("高一".equals(gradeLevel)) {
							obj.setGradeLevelId(10);
						}else if("高二".equals(gradeLevel)) {
							obj.setGradeLevelId(11);
						}else if("高三".equals(gradeLevel)) {
							obj.setGradeLevelId(12);
						}else throw new ServiceException("试卷模板年级填写不规范（内容参照试卷管理页面年级下拉框）");
						
						//学期
						term = term.replaceAll("\\s*", "");
						if("上学期".equals(term)) {
							obj.setTerm(1);
						}else if("下学期".equals(term)) {
							obj.setTerm(2);
						}else if("不限".equals(term)) {
							
						}else throw new ServiceException("试卷模板学期填写不规范（上学期、下学期、不限）");

						//题型
						paperType = paperType.replaceAll("\\s*", "");
						if("听力".equals(paperType)) {
//							obj.setPaperType(1);
							obj.setQuestionType(1);
						}else if("听说".equals(paperType)) {
//							obj.setPaperType(2);
							obj.setQuestionType(2);
						}else throw new ServiceException("试卷模板题型填写不规范（听力；听说）");

						//类型
						questionType = questionType.replaceAll("\\s*", "");
						if("中考".equals(questionType)) {
//							obj.setQuestionType(1);
							obj.setPaperType(1);
						}else if("高考".equals(questionType)) {
//							obj.setQuestionType(2);
							obj.setPaperType(2);
						}else if("模拟".equals(questionType)) {
//							obj.setQuestionType(3);
							obj.setPaperType(3);
						}else if("同步测验".equals(questionType)) {
//							obj.setQuestionType(4);
							obj.setPaperType(4);
						}else if("期中".equals(questionType)) {
//							obj.setQuestionType(5);
							obj.setPaperType(5);
						}else if("期末".equals(questionType)) {
//							obj.setQuestionType(6);
							obj.setPaperType(6);
						}else if("真题".equals(questionType)) {
//							obj.setQuestionType(7);
							obj.setPaperType(7);
						}else throw new ServiceException("试卷模板学期类型不规范（中考；高考；模拟；同步测验；期中；期末；真题）");
						
					}else throw new ServiceException("试卷模板信息不完整."+"第1行");

					List<PaperSubjectEntity> subjects = obj.getList();
					
					PaperSubjectEntity temp = null;
					
					for (int rowNum = 3; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
						
						XSSFRow xssfRow = xssfSheet.getRow(rowNum);
						//判断结束标志
						if(xssfRow == null || (xssfRow.getCell(0) != null 
								&& getValue(xssfRow.getCell(0)) != null && getValue(xssfRow.getCell(0)).toLowerCase().contains("break")))  break;
						
						//判断是否是大题信息
						if (xssfRow.getCell(1) != null && StringUtils.isNotBlank(getValue(xssfRow.getCell(1)))) {
							if(temp != null) {
//								temp.setSubjectNum((subjects.size()+1)+ "." +temp.getSubjectNum());
								subjects.add(temp);
							}
							temp = new PaperSubjectEntity();
							String typestr 	= getValue(xssfRow.getCell(1));
							String subject 	= getValue(xssfRow.getCell(2));
							String audio 	= getValue(xssfRow.getCell(3));
							
							if(StringUtils.isNotBlank(subject))temp.setSubject(subject);
							
							//TODO:文件
//							if(StringUtils.isNotBlank(audio))temp.setAudio(path+File.separator+audio);
							//音频
	                    	if(StringUtils.isNotBlank(audio)) {
	                    		//拼接路径
	                    		String filepath = path + File.separator +audio.replace('\\',File.separatorChar);
	                    		//判断音频文件是否存在
	                    		String type = ResourceImportUtil.hanFlie(filepath, audio, 1, "第"+(rowNum+1)+"行");
	                    		//设置路径
	                    		temp.setAudio(filepath+type);
	                    	}
							
							
							int type = (int) Double.parseDouble(typestr);
							temp.setType(type);
							
							switch (type) {
							case 1: temp.setSubjectNum("听后回答"); break;
							case 2: temp.setSubjectNum("听后选择"); break;
							case 3: temp.setSubjectNum("听后记录及转述"); break;
							case 4: temp.setSubjectNum("短文朗读"); break;
							case 5: temp.setSubjectNum("情景问答"); break;
							case 6: temp.setSubjectNum("话题简述"); break;
							default: throw new ServiceException("试卷模板题型不规范（1-6）。第"+(rowNum+1)+"行");
							}
							temp.setList(new ArrayList<>());
						}else {//小题信息
							
							if(temp == null) throw new ServiceException("试卷模板试题格式错误。第"+(rowNum+1)+"行");
							
							//PaperSubjectDetailEntity题目相关
							String readTimes 	= getValue(xssfRow.getCell(4));
							String writeTime 	= getValue(xssfRow.getCell(5));
							String guide 	= getValue(xssfRow.getCell(6));
							String guideAudio 	= getValue(xssfRow.getCell(7));
							String questionAudio 	= getValue(xssfRow.getCell(8));
							String repeatCount 	= getValue(xssfRow.getCell(9));
							String originalText 	= getValue(xssfRow.getCell(10));

							String no 	= getValue(xssfRow.getCell(11));
							String typestr 	= getValue(xssfRow.getCell(12));
							String question 	= getValue(xssfRow.getCell(13));
							String score 	= getValue(xssfRow.getCell(17));
							
							//PaperOptionEntity选项相关
							String result 	= getValue(xssfRow.getCell(14));
							String correctResult 	= getValue(xssfRow.getCell(15));
							String pointResult 	= getValue(xssfRow.getCell(16));
							
							//判断信息完整性
							if(StringUtils.isNotBlank(question) && StringUtils.isNotBlank(typestr)
									&& StringUtils.isNotBlank(score) && StringUtils.isNotBlank(writeTime)) {
								
								//小题信息
								PaperSubjectDetailEntity sd = new PaperSubjectDetailEntity();
								if(StringUtils.isNotBlank(readTimes)) sd.setReadTime((int) Double.parseDouble(readTimes));
								if(StringUtils.isNotBlank(writeTime)) sd.setWriteTime((int) Double.parseDouble(writeTime));
								sd.setGuide(guide);
								sd.setOriginalText(originalText);
								sd.setQuestion(question);
								sd.setScore(Double.parseDouble(score));
								sd.setOriginalText(originalText);
								try {
									sd.setRepeatCount(Integer.parseInt(repeatCount)>0?Integer.parseInt(repeatCount):1);
								} catch (Exception e) {
									sd.setRepeatCount(1);
								}
								Integer questionNo = 1;
								try {
									questionNo = (int) Double.parseDouble(no);
								} catch (Exception e) {
//									throw new ServiceException("试卷模板小题题号只能为数字。第"+(rowNum+1)+"行");
								}
								sd.setQuestionNo(questionNo);
								//TODO:文件
//								if(StringUtils.isNotBlank(guideAudio))sd.setGuideAudio(path+File.separator+guideAudio);
//								if(StringUtils.isNotBlank(questionAudio))sd.setQuestionAudio(path+File.separator+questionAudio);
								//音频
		                    	if(StringUtils.isNotBlank(guideAudio)) {
		                    		//拼接路径
		                    		String filepath = path + File.separator +guideAudio.replace('\\',File.separatorChar);
		                    		//判断音频文件是否存在
		                    		String type = ResourceImportUtil.hanFlie(filepath, guideAudio, 1, "第"+(rowNum+1)+"行");
		                    		//设置路径
		                    		sd.setGuideAudio(filepath+type);
		                    	}
		                    	//音频
		                    	if(StringUtils.isNotBlank(questionAudio)) {
		                    		//拼接路径
		                    		String filepath = path + File.separator +questionAudio.replace('\\',File.separatorChar);
		                    		//判断音频文件是否存在
		                    		String type = ResourceImportUtil.hanFlie(filepath, questionAudio, 1, "第"+(rowNum+1)+"行");
		                    		//设置路径
		                    		sd.setQuestionAudio(filepath+type);
		                    	}
								
								
								
								//类型
								typestr = typestr.replaceAll("\\s*", "");
								if("听答".equals(typestr)) {
									sd.setType(1);
									PaperOptionEntity op = new PaperOptionEntity();
									op.setCorrectResult(correctResult); 
									op.setPointResult(pointResult);
									sd.setOption(op);
									//小题加入大题对象
									temp.getList().add(sd);
								}else if("听选".equals(typestr)) {
									sd.setType(2);
									PaperOptionEntity op = new PaperOptionEntity();
									op.setCorrectResult(correctResult);
									op.setResult(result);
									sd.setOption(op);
									//小题加入大题对象
									temp.getList().add(sd);
								}else if("听说".equals(typestr) || "看答".equals(typestr)) {
									sd.setType(4);
									PaperOptionEntity op = new PaperOptionEntity();
									op.setCorrectResult(correctResult);
									op.setPointResult(pointResult);
									sd.setOption(op);
									//小题加入大题对象
									temp.getList().add(sd);
								}else if("朗读".equals(typestr)) {
									sd.setType(5);
									//小题加入大题对象
									temp.getList().add(sd);
								}else if("填空".equals(typestr)) {
									
									sd.setType(3);
									String[] corrects = correctResult.split("@@");
									List<PaperOptionEntity> ops = new ArrayList<>();
									for (String correct : corrects) {
										PaperOptionEntity op = new PaperOptionEntity();
										op.setCorrectResult(correct);
										ops.add(op);
									}
									//TODO:填空题问题是图片文件
//									if(StringUtils.isNotBlank(question))sd.setQuestion(path+File.separator+question);
//									else throw new ServiceException("试卷模板填空题缺少图片。第"+(rowNum+1)+"行");
									//图片
			                    	if(StringUtils.isNotBlank(question)) {
			                    		//拼接路径
			                    		String filepath = path+File.separator +question.replace('\\',File.separatorChar);
			                    		//判断图片文件是否存在
			                    		String type = ResourceImportUtil.hanFlie(filepath, question, 2, "第"+(rowNum+1)+"行");
			                    		//设置路径
			                    		sd.setQuestion(filepath+type);
			                    	}else throw new ServiceException("试卷模板填空题缺少图片。第"+(rowNum+1)+"行");
									
									sd.setOption(ops.get(0));
									temp.getList().add(sd);
									
									ops.remove(0);
									for (PaperOptionEntity op : ops) {
										PaperSubjectDetailEntity psd = new PaperSubjectDetailEntity();
										psd.setOption(op);
										psd.setType(3);
										psd.setQuestionNo(sd.getQuestionNo());
										psd.setScore(sd.getScore());
										temp.getList().add(psd);
									}
									
								}else throw new ServiceException("试卷模板小题类型不规范（听答；听选；填空；听说；朗读；看答）。第"+(rowNum+1)+"行");
								
								
							}else {

//								if(StringUtils.isNotBlank(question) && StringUtils.isNotBlank(typestr)
//										&& StringUtils.isNotBlank(score) && StringUtils.isNotBlank(writeTime)
								
								if(StringUtils.isBlank(question)) throw new ServiceException("题目不能为空。第"+(rowNum+1)+"行");
								if(StringUtils.isBlank(typestr)) throw new ServiceException("类型不能为空。第"+(rowNum+1)+"行");
								if(StringUtils.isBlank(score)) throw new ServiceException("分数不能为空。第"+(rowNum+1)+"行");
								if(StringUtils.isBlank(writeTime)) throw new ServiceException("答题时间不能为空。第"+(rowNum+1)+"行");
								
								
								
							}
						}
					}
					//加入大题
					subjects.add(temp);
				}
			}
		} catch (Exception e) {
			throw e;
		}finally {
			if(is != null) is.close();
			if(xssfWorkbook != null) xssfWorkbook.close();
		}
		return obj;
	}

	@SuppressWarnings({ "deprecation", "static-access" })
	private static String getValue(XSSFCell xssfRow) {
		if(xssfRow == null) return null;
		if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
			return String.valueOf(xssfRow.getBooleanCellValue());
		} else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
			if (DateUtil.isCellDateFormatted(xssfRow)) {  
                //  如果是date类型则 ，获取该cell的date值  
				return new SimpleDateFormat("yyyy/MM/dd").format(DateUtil.getJavaDate(xssfRow.getNumericCellValue()));  
            } else { // 纯数字  
            	return String.valueOf(xssfRow.getNumericCellValue());  
            } 
		} else {
			return String.valueOf(xssfRow.getStringCellValue());
		}
	}

}

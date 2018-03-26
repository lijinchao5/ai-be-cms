package com.xuanli.oepcms.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.springframework.web.multipart.MultipartFile;

import com.xuanli.oepcms.exception.ServiceException;

/**
 * 
 * @ClassName: ResourceImportUtil
 * @Description: 基础资源导入工具类
 * @author WangMeng
 * @date 2018年2月9日
 *
 */
public class ResourceImportUtil {

	/**
	 * 
	 * @Description: 读取word表格
	 * 
	 * @return	List<List<String>> 
	 * @author  WangMeng
	 * @throws IOException 
	 */
	public static List<List<String>> readDocxTable(String filePath) throws Exception {
	    List<List<String>> list = null;
	    FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(filePath);
			// 获取所有表格
			@SuppressWarnings("resource")
			List<XWPFTable> tables = new XWPFDocument(inputStream).getTables();
			if(tables.size()>0) {
				
				XWPFTable table = tables.get(0);
				list = new ArrayList<>();
			    // 获取表格的行
			    List<XWPFTableRow> rows = table.getRows();
			    for (XWPFTableRow row : rows) {
			        // 获取表格的每个单元格
			        List<XWPFTableCell> tableCells = row.getTableCells();
					List<String> rowstr = new ArrayList<>();
			        for (XWPFTableCell cell : tableCells) {
			             // 获取单元格的内容
			             String text = cell.getText();
			             rowstr.add(text);
			        }
			        list.add(rowstr);
			    }
			}else {
				throw new ServiceException("所属课文件格式不规范，至少存在一个表格。");
			}
			inputStream.close();
		} catch (Exception e) {
			if(inputStream != null) inputStream.close();
			e.printStackTrace();
		}
		return list;
	}
	
	public static String hanFlie(String path, String name, int filetype, String tip) {
		
		//判断音频文件是否存在
		if(filetype == 1) {
			if(!new File(path+".mp3").exists()) throw new ServiceException(tip + "---文件不存在【"
					+path+".mp3】");
			return ".mp3";
		}else if(filetype == 2) {//判断图片文件是否存在

    		String[] types = new String[] {"jpeg","jpg","JPG","gif","GIF","png","PNG","bmp","BMP"};
    		for (String type : types) {
        		if(new File(path+"."+type).exists()) {
        			return "."+type;
        		}
			}
    		throw new ServiceException(tip+"---图片文件不存在【"+path+"】");
		}else {

    		throw new ServiceException("文件验证类型错误");
		}
	}
	
	/**
	 * @Description: 存储上传的文件
	 * 
	 * @return boolean
	 * @author WangMeng
	 */
	public static boolean saveFile(MultipartFile file, String filePath) {
		// 判断文件是否为空
		if (!file.isEmpty()) {
			try {
				File saveFile = new File(filePath);
				File fileParent = saveFile.getParentFile();  
				if(!fileParent.exists()){  
				    fileParent.mkdirs();  
				}
				file.transferTo(new File(filePath));//转存文件
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else
			return false;
	}

	/**
	 * 
	 * @Description: 解压Zip文件
	 * 
	 * @return	void 
	 * @author  WangMeng
	 */
	public static void unZip(String path) {
		String savepath = path.substring(0, path.lastIndexOf(".")) + File.separator; // 保存解压文件目录
		unZip(path, savepath);
	}
	public static void unZip(String path, String savepath) {
		int count = -1;
		int buffer = 2048;  
		File file = null;
		InputStream is = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		
		new File(savepath).mkdir(); // 创建保存目录
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(path, "gbk"); // 解决中文乱码问题
			Enumeration<?> entries = zipFile.getEntries();

			while (entries.hasMoreElements()) {
				byte buf[] = new byte[buffer];

				ZipEntry entry = (ZipEntry) entries.nextElement();

				String filename = entry.getName();
				boolean ismkdir = false;
				if (filename.lastIndexOf("/") != -1) { // 检查此文件是否带有文件夹
					ismkdir = true;
				}
				filename = savepath + filename;

				if (entry.isDirectory()) { // 如果是文件夹先创建
					file = new File(filename);
					file.mkdirs();
					continue;
				}
				file = new File(filename);
				if (!file.exists()) { // 如果是目录先创建
					if (ismkdir) {
						new File(filename.substring(0, filename.lastIndexOf("/"))).mkdirs(); // 目录先创建
					}
				}
				file.createNewFile(); // 创建文件

				is = zipFile.getInputStream(entry);
				fos = new FileOutputStream(file);
				bos = new BufferedOutputStream(fos, buffer);

				while ((count = is.read(buf)) > -1) {
					bos.write(buf, 0, count);
				}
				bos.flush();
				bos.close();
				fos.close();

				is.close();
			}

			zipFile.close();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (bos != null) {
					bos.close();
				}
				if (fos != null) {
					fos.close();
				}
				if (is != null) {
					is.close();
				}
				if (zipFile != null) {
					zipFile.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

package com.xuanli.oepcms.service;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xuanli.oepcms.BaseTest;
import com.xuanli.oepcms.util.AliOSSUtil;

public class AliOSSUtilTest extends BaseTest {

	@Autowired
	AliOSSUtil aliOSSUtil;
    
    
    @Test
    public void add() throws Exception{
    	
    	
//    	String filep = "C:\\Users\\Admin\\Desktop\\startrecord1.mp3";
//    	
//    	
//    	String path = aliOSSUtil.uploadFile(new FileInputStream(new File(filep)), 
//				"tip" , "mp3");
//    	System.out.println(path);
    }

}

package com.xuanli.oepcms.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xuanli.oepcms.BaseTest;
import com.xuanli.oepcms.entity.WordsEntity;

public class WordsServiceTest extends BaseTest {
	
    @Autowired
    private WordsService wordsService;
    
    
    @Test
    public void add() throws Exception{
    	
//    	String type = "4";
//    	
//    	String folderpath = "G:\\单词音频库\\香港朗文单词";
//    	
//    	File folder = new File(folderpath);
//    	
//    	TreeMap<String, WordsEntity> treeMap = new TreeMap<String, WordsEntity>();
//    	
//    	File[] files = folder.listFiles();
//    	
//    	for (File file : files) {
//    		
//			String name = file.getName();
//			String canonicalPath = file.getCanonicalPath();
//			
//			if(!name.endsWith(".mp3")) continue;
//			name = name.replaceAll(".mp3", "").trim();
//			String wordSpell = name.replaceAll("1", "").replaceAll("2", "");
//			
//			WordsEntity obj = null;
//			if(treeMap.containsKey(wordSpell)) obj = treeMap.get(wordSpell);
//			else {
//				obj = new WordsEntity();
//				obj.setType(type);
//				obj.setWordSpell(wordSpell);
//				treeMap.put(wordSpell, obj);
//			}
//			if(name.endsWith("1")) {
//				obj.setAudioPathM(canonicalPath);
//			}else if(name.endsWith("2")) {
//				obj.setAudioPathW(canonicalPath);
//			}else {
//				if(StringUtils.isBlank(obj.getAudioPathM())) obj.setAudioPathM(canonicalPath);;
//			}
//		}
//    	List<WordsEntity> list = new ArrayList<WordsEntity>(treeMap.values());
////    	System.out.println(JSON.toJSON(list));
//    	wordsService.saveWordsList(list);
    }

}

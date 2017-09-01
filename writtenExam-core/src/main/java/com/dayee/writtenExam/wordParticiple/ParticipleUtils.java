package com.dayee.writtenExam.wordParticiple;


import java.util.ArrayList;
import java.util.List;

import com.dayee.writtenExam.framework.util.CollectionUtils;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;

/**
 * 分词工具类
 * Created by yq.song on 2016/12/9.
 */
public class ParticipleUtils {
	
	/**
	 * 根据字符串返回分词结果
	 * @param str
	 * @return
	 */
	public static List<String> participle(String str){
		
		List<Word> wordResults=WordSegmenter.seg(str);
		
		List<String> strResults=null;
		
		if(CollectionUtils.isNotEmpty(wordResults)){
			strResults=new ArrayList<String>();
			for(Word word:wordResults){
				strResults.add(word.getText());
			}
		}
		return strResults;
	}
	
	public static void main(String[] args) {
		System.out.println(participle("上海市"));
	}
	
}

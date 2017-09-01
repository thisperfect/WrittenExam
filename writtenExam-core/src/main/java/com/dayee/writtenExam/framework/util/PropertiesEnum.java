package com.dayee.writtenExam.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * Properties文件枚举类
 * Created by yq.song on 2016/10/28.
 */
public enum PropertiesEnum {
	
	/**返回给前端的提示信息、后端存储的中文语句 **/
	PROMPT("prompt"),
	
	RESUME_CONFIG("resume");
	
	private String path;
	
	private Properties prop=null;
	
	PropertiesEnum(String path) {
		this.path = path;
	}
	
	/**
	 * 获取提示消息
	 * @param key
	 * @return
	 */
	public String getProperty(String key){
		
		if(StringUtils.isBlank(key)){
			return null;
		}
		
		if (prop==null){
			init(path);
		}
		return prop.getProperty(key);
	}
	
	/**
	 * 获取带占位符的提示消息
	 * @param key
	 * @return
	 */
	public String getPlaceHolderValue(String key,Object... objects){
		
		if(StringUtils.isBlank(key)){
			return null;
		}
		
		if (prop==null){
			init(path);
		}
		
		String value=prop.getProperty(key);
		return MessageFormat.format(value,objects);
	}
	
	
	private void init(String path){
		
		prop=new Properties();
		InputStream in=PropertiesEnum.class.getClassLoader().getResourceAsStream("/"+path+".properties");
		try {
			InputStreamReader bf = new InputStreamReader(in,"utf-8");
			prop.load(bf);
			in.close();
			bf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

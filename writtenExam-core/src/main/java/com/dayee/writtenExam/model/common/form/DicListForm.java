
package com.dayee.writtenExam.model.common.form;

import java.util.LinkedHashMap;
import java.util.Map;

public class DicListForm {

    private String              standardCode;

    //单个层级
    private Map<String, String> list = null;
    
    //多个层级 
    private Map<String, LevelDicForm> levelList = null;//二级列表（专业类别，地址）

    public void addDic(String dicId, String dicName) {
        if(list==null){
            list=new LinkedHashMap<String, String>();
        }
        list.put(dicId, dicName);
    }

    public String getStandardCode() {

        return standardCode;
    }

    public void setStandardCode(String standardCode) {

        this.standardCode = standardCode;
    }

    public Map<String, String> getList() {

        return list;
    }

    public void setList(Map<String, String> list) {
    	if(list==null){
    		list=new LinkedHashMap<String, String>();
    	}
        this.list = list;
    }

	public Map<String, LevelDicForm> getLevelList() {
		return levelList;
	}

	public void setLevelList(Map<String, LevelDicForm> levelList) {
		if(levelList==null){
			levelList=new LinkedHashMap<String, LevelDicForm>();
		}
		this.levelList = levelList;
	}
	
	public void putList(String key,String value){
		if(list==null){
    		list=new LinkedHashMap<String, String>();
    	}
		list.put(key, value);
	}
	public void putLevelList(String key,LevelDicForm levelDicForm){
		if(levelList==null){
			levelList=new LinkedHashMap<String, LevelDicForm>();
		}
		levelList.put(key, levelDicForm);
	}
    
}

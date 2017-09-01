/*
 * IDEADP是IDEAMOMENT Develop Platform的缩写。
 * 由北京创想时刻信息技术有限公司研发。项目提供各种常见问题的最佳实践和解决方案，用于提高各产品和项目的开发效率。
 * 
 * Copyright @ www.ideamoment.com
 * All right reserved.
 */
package com.dayee.writtenExam.framework.restful.json;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chinakite Zhang
 *
 */
public class JsonDataPackage implements Serializable {
	
	private static final long serialVersionUID = -4587663936885332591L;

	private Map<String, JsonData> jsonPackage = new HashMap<String, JsonData>();
	
	public void addJsonData(String key, JsonData jsonData) {
		jsonPackage.put(key, jsonData);
	}
	
	public void removeJsonData(String key) {
		jsonPackage.remove(key);
	}

	public String getJson() {
		StringBuilder result = new StringBuilder("{");
		int i=0;
		for(String key : jsonPackage.keySet()) {
			if(i > 0) {
				result.append(",");
			}
			JsonData jd = jsonPackage.get(key);
			result.append("\"").append(key).append("\":")
			      .append(jd.getJson());
			i++;
		}
		result.append("}");
		return result.toString();
	}

}

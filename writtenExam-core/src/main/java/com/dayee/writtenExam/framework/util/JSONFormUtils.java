package com.dayee.writtenExam.framework.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

public class JSONFormUtils {
    
    /***
     * 将表单formjson转化成Map<String,String>
     * json格式:[{"name":"","value":""},{"name":"","value":""}]
     * @param json
     * @return
     */
    public static Map<String,String> transFormJsonToMap(String json){
        JSONArray parseArray = JSON.parseArray(json);
        Map<String,String> map = new HashMap<String,String>();
        for (int i = 0; i < parseArray.size(); i++) {
            Map<String,String> mapss = (Map<String, String>) parseArray.get(i);
            map.put(mapss.get("name"), mapss.get("value"));
        }
        return map;
    }
}

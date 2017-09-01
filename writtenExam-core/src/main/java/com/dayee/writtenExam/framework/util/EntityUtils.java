package com.dayee.writtenExam.framework.util;

import java.util.Date;

import com.dayee.writtenExam.framework.usercontext.UserContext;
import com.dayee.writtenExam.model.CorpPartitionEntity;
import com.dayee.writtenExam.model.HistoriableEntity;
import com.ideamoment.ideadata.generator.GeneratorFactory;


public class EntityUtils {

    public static void initCreateInfo(HistoriableEntity entity, String userId) {

        entity.setCreateTime(new Date());
        entity.setCreator(userId);
        entity.setModifier(userId);
        entity.setModifyTime(new Date());
    }
    
    public static  void initCreateInfo(HistoriableEntity entity){
        
        entity.setCreateTime(new Date());
        entity.setCreator(UserContext.getCurrentUserId());     
        entity.setModifier(UserContext.getCurrentUserId());
        entity.setModifyTime(new Date());
    }
    
    public static void initModifInfo(CorpPartitionEntity entity){
        
        entity.setModifier(UserContext.getCurrentUserId());
        entity.setModifyTime(new Date());
    }
    
    public static String genUUID(Object obj){
        
        String uuid = (String) GeneratorFactory.getInstance().getGenerator("uuid").generate(obj);
        return uuid;
    }
    
    
}

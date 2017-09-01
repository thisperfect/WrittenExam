
package com.dayee.writtenExam.listener;

import com.dayee.writtenExam.model.mongo.SequencePO;
import com.dayee.writtenExam.mongo.annotation.GeneratedValue;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;

/**
 * @Author: dayee_yangkai
 * @Description: 监听器，实现Id的自增
 * @Date: Created in 18:09 2017/7/12
 * @Modified By:
 * @Version 1.0.1 
 */
@Component
public class SaveMongoEventListener extends AbstractMongoEventListener<Object> {

    @Resource(name = "mongoTemplate")
    private MongoTemplate mongoTemplate;

    @Override
    public void onBeforeConvert(Object source) {

        final Object source_ = source;
        if (source != null) {
            /* 遍历所有字段，并对每一个字段做回掉处理 */
            ReflectionUtils.doWithFields(source.getClass(),
                                         new ReflectionUtils.FieldCallback() {

                                             public void doWith(Field field)
                                                     throws IllegalArgumentException,
                                                     IllegalAccessException {

                                                 ReflectionUtils
                                                         .makeAccessible(field);

                                                 if (field
                                                         .isAnnotationPresent(GeneratedValue.class)) {
                                                     // 设置自增ID
                                                     if (field
                                                             .get(source_) == null) {
                                                         field.set(source_,
                                                                   getNextId(source_
                                                                           .getClass()
                                                                           .getSimpleName()));
                                                     }
                                                 }
                                             }
                                         });
        }
    }

    /**
     * 获取下一个自增ID
     *
     * @param collName
     *            集合名
     * @return
     */
    private String getNextId(String collName) {

        Query query = new Query(Criteria.where("collName").is(collName));
        Update update = new Update();
        update.inc("seqId", 1);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.upsert(true);
        options.returnNew(true);
        SequencePO seqId = mongoTemplate.findAndModify(query, update, options,
                                                       SequencePO.class);

        return seqId.getSeqId().toString();
    }

}
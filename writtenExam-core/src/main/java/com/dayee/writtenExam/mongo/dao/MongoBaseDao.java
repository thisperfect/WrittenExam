
package com.dayee.writtenExam.mongo.dao;

import com.dayee.writtenExam.framework.util.ReflectUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Author: dayee_yangkai
 * @Description: mongdb的操作基类
 * @Date: Created in 18:09 2017/7/12
 * @Modified By:
 * @Version 1.0.1 
 */
public class MongoBaseDao<T> {

    @Autowired
    private MongoTemplate mongoTemplate;

    /* 获取当前数据库所有的集合名称，类似与show collections */
    /* 类似：获取所有表名 */
    public Set<String> findCollectionNames() {

        return mongoTemplate.getCollectionNames();
    }

    /* 获取当前操作的集合名称 */
    public String findCollectionName() {

        return mongoTemplate.getCollectionName(this.getEntityClass());
    }

    /* 通过_id获取到对应的文档对象 */
    /* 类似：根据id获取行数据 */
    public T findOneDocById(String id) {

        return mongoTemplate.findById(id, this.getEntityClass());
    }

    /* 通过条件获取到对应的对象 */
    /* 只操作简单的属性相等条件 */
    public T findOneDocByParams(Map<String, Object> params) {

        return findOneDocBase(params, null);
    }

    /* 通过条件获取到对应的对象 */
    /* BasicQuery与Query的不同之处在于 */
    private T findOneDocBase(Map<String, Object> params,
                             List<String> showFields) {

        DBObject queryObject = null;
        DBObject showObject = null;
        if (!CollectionUtils.isEmpty(params)) {
            queryObject = new BasicDBObject();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                queryObject.put(entry.getKey(), entry.getValue());
            }
        }
        if (!CollectionUtils.isEmpty(showFields)) {
            showObject = new BasicDBObject();
            for (String field : showFields) {
                showObject.put(field, 1);
            }
        }
        Query query = null;
        if (showFields == null) {
            query = new BasicQuery(queryObject);
        } else {
            query = new BasicQuery(queryObject, showObject);
        }
        return mongoTemplate.findOne(query, this.getEntityClass());
    }

    /* 通过_id集合获取到对应的对象集合 */
    public List<T> findDocsByIds(Object[] ids) {

        Query query = new Query();
        query.addCriteria(new Criteria("_id").in(ids));
        return mongoTemplate.find(query, this.getEntityClass());
    }

    /* 通过_id集合获取到对应的对象集合 */
    public List<T> findDocsByInRange(String fieldName, Object[] params) {

        Query query = new Query();
        query.addCriteria(new Criteria(fieldName).in(params));
        return mongoTemplate.find(query, this.getEntityClass());
    }

    /* 根据集合名获取集合对象 */
    /* 类似：获取表里所有数据 */
    public List<T> findAllDocs() {

        return mongoTemplate.findAll(this.getEntityClass());
    }

    /* 构建简单的query查询条件对象 */
    public Query setQuery(Map<String, Object> params) {

        Query query = new Query();
        Criteria criteria = new Criteria();
        if (!CollectionUtils.isEmpty(params)) {
            for (Map.Entry<String, Object> param : params.entrySet()) {
                criteria.and(param.getKey()).is(param.getValue());
            }
        }
        query.addCriteria(criteria);
        return query;
    }

    /* 构建简单的Update对象 */
    public Update setUpdate(Map<String, Object> updateMap) {

        Update update = new Update();
        if (!CollectionUtils.isEmpty(updateMap)) {
            for (Map.Entry<String, Object> map : updateMap.entrySet()) {
                update.set(map.getKey(), map.getValue());
            }
        }
        return update;
    }

    /* 更新符合条件的第一条记录 */
    public void updateFirstDoc(Map<String, Object> params,
                               Map<String, Object> updateMap) {

        mongoTemplate.updateFirst(setQuery(params), setUpdate(updateMap),
                                  this.getEntityClass());
    }

    /* 批量更新 */
    public void updateMultiDoc(Map<String, Object> params,
                               Map<String, Object> updateMap) {

        mongoTemplate.updateMulti(setQuery(params), setUpdate(updateMap),
                                  this.getEntityClass());
    }

    /* 根据_id删除文档 */
    /* 类似：删除一行记录 */
    public void deleteDocById(String id) {

        mongoTemplate.remove(new Query(new Criteria("_id").is(id)),
                             this.getEntityClass());
    }

    /* 根据删除文档 */
    /* 类似：删除一行记录 */
    public void deleteDocByParams(Map<String, Object> params) {

        mongoTemplate.remove(setQuery(params), this.getEntityClass());
    }

    /* 插入一条文档 */
    /* 类似：插入一条行记录 */
    /* MongoTemplate提供了两种存储文档方式，分别是save和insert方法 */
    /* save：我们在新增文档时，如果有一个相同_ID的文档时，会覆盖原来的，因为是整个文档的覆盖，所以不推荐使用 */
    /* insert：我们在新增文档时，如果有一个相同的_ID时，就会新增失败 */
    /* insert不需要映射对象，因为保存的对象中就包含了collectionName */
    public void insertDoc(Object doc) {

        mongoTemplate.insert(doc);
    }

    /* 批量插入多个文档 */
    public void insertDocs(Collection<Object> docs) {

        mongoTemplate.insert(docs);
    }

    /* 不存在时插入，已存在时不做任何操作 */
    public void uninsertDocByParams(Object doc, Map<String, Object> params) {

        if (!isExist(params))
            insertDoc(doc);
    }

    /* 不存在时插入，已存在时更新 */
    public void upsertDocById(Object doc, String id) {

        mongoTemplate.findAndRemove(new Query(new Criteria("_id").is(id)),
                                    this.getEntityClass());

        mongoTemplate.insert(doc);
    }

    /* 不存在时插入，已存在时更新 */
    public void upsertDocByParams(Object doc, Map<String, Object> params) {

        mongoTemplate.findAndRemove(setQuery(params), this.getEntityClass());

        mongoTemplate.insert(doc);
    }

    public boolean isExist(Map<String, Object> params) {

        List<String> showFields = new ArrayList<String>();
        showFields.add("_id");

        return findOneDocBase(params, showFields) != null;
    }

    /* 执行命令 */
    public CommandResult executeCommand(String command) {

        return mongoTemplate.executeCommand(command);
    }

    /* 内嵌数组文档添加单一对象 */
    public void insideArraySaveOne(String arrayKey,
                                   Object obj,
                                   Map<String, Object> params) {

        Update update = new Update();
        // update.addToSet(tag, obj);
        update.push(arrayKey, obj);
        mongoTemplate.upsert(setQuery(params), update, this.getEntityClass());
    }

    /* 内嵌数组文档添加对象数组 */
    public void insideArraySaveAll(String arrayKey,
                                   Object[] objs,
                                   Map<String, Object> params) {

        Update update = new Update();
        update.pushAll(arrayKey, objs);
        mongoTemplate.upsert(setQuery(params), update, this.getEntityClass());
    }

    /* 内嵌数组文档删除 */
    /* unset删除内嵌文档数组对象中的单个对象时会导致原来的值会替换为 null */
    /* 所以还是一致使用pull来删除数组中元素 */
    /* pull的对象可以为一个真实的对象，也可作为一个query对象 */
    /* $ 定位操作符，用来定位查询文档已经匹配的元素 */
    public void insideArrayDelete(String arrayKey,
                                  Map<String, Object> docParams,
                                  Map<String, Object> arrayParams) {

        Update update = new Update();
        update.pull(arrayKey, setQuery(arrayParams));
        mongoTemplate.updateFirst(setQuery(docParams), update,
                                  this.getEntityClass());
    }

    /* 测试嵌套数组匹配对象 */
    /* elemMatch */
    @Deprecated
    public T isExistInArray(String id, String qid) {

        Query query = new Query();
        Criteria criteria = new Criteria("_id").is(id).and("quesList")
                .elemMatch(new Criteria("_id").is(qid));
        query.addCriteria(criteria);
        return mongoTemplate.findOne(query, this.getEntityClass());
    }

    /* 通过反射, 获得Class定义中声明的父类的泛型参数的类型 */
    private Class<T> getEntityClass() {

        return ReflectUtils.getSuperClassGenricType(getClass());
    }
}

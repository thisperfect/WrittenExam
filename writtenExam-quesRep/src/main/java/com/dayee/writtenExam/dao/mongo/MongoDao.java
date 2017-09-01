package com.dayee.writtenExam.dao.mongo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by yq.song on 2016/9/22.
 */
@Component
public class MongoDao {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public void save(Object object) {

		mongoTemplate.save(object);
	}

	public <T> T findById(Object id, Class<T> clazz) {

		return mongoTemplate.findById(id, clazz);
	}

	public void remove(Object object) {

		mongoTemplate.remove(object);
	}
}

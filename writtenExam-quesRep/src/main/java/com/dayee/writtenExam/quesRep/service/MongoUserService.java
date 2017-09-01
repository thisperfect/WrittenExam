
package com.dayee.writtenExam.quesRep.service;

import com.dayee.writtenExam.dao.mongo.MongoUserDao;
import com.dayee.writtenExam.model.mongo.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 13:24 2017/7/13
 * @Modified By:
 * @Version  
 */
@Service
public class MongoUserService {

    @Autowired
    private MongoUserDao mongoUserDao;

    public void saveUser() {

        UserPO userPO = new UserPO();
        userPO.setAge(12);
        userPO.setName("lugi");
        mongoUserDao.insertDoc(userPO);
    }

    public UserPO getUserById(String Id) {

        return mongoUserDao.findOneDocById(Id);
    }

    public UserPO getUserByParams() {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("_id", "1");
        List<String> fields = new ArrayList<String>();
        fields.add("name");

        return mongoUserDao.findOneDocByParams(params);
    }
}

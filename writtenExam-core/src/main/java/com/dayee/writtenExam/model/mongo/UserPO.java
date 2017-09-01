
package com.dayee.writtenExam.model.mongo;

import com.dayee.writtenExam.mongo.annotation.GeneratedValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @Author: dayee_yangkai
 * @Description:
 * @Date: Created in 14:29 2017/7/13
 * @Modified By:
 * @Version  
 */
@Document(collection = "myUsers")
public class UserPO {

    @GeneratedValue
    @Id
    private String  id;

    private String  name;

    private Integer age;

    private Boolean sex;

    private Date    date;

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public Integer getAge() {

        return age;
    }

    public void setAge(Integer age) {

        this.age = age;
    }

    public Boolean getSex() {

        return sex;
    }

    public void setSex(Boolean sex) {

        this.sex = sex;
    }

    public Date getDate() {

        return date;
    }

    public void setDate(Date date) {

        this.date = date;
    }
}

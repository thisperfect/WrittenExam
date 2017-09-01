package com.dayee.writtenExam.pc.dao;

import com.dayee.writtenExam.pc.model.LoginUser;
import com.ideamoment.ideajdbc.IdeaJdbc;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangchen on 2017/7/8.
 */
@Repository
public class LoginUserDao {

    public List<LoginUser> getLoginUser(){
        String sql = "select * from t_login_user";
        return IdeaJdbc.query(sql).listTo(LoginUser.class);
    }
}
